package org.nrna.services;

import org.nrna.exception.ResourceNotFoundException;
import org.nrna.models.UserAddress;
import org.nrna.models.UserProfileAndAddress;
import org.nrna.models.dto.UserDetailsImpl;
import org.nrna.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.nrna.models.dto.User;
import org.nrna.models.dto.Address;
import org.nrna.models.request.LoginRequest;
import org.nrna.models.request.SignupRequest;
import org.nrna.models.response.MessageResponse;
import org.nrna.models.response.UserResponse;
import org.nrna.repository.AddressRepository;
import org.nrna.repository.UserRepository;
import org.nrna.security.jwt.JwtUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EmailService emailService;
	
	UserService(){
		
	}
	
	public ResponseEntity<?> signUp(SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("EmailExist"));
		}

		User user = new User();
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setEmail(signUpRequest.getEmail());
		userRepository.save(user);

		return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
	}

	public ResponseEntity<?> logout(){
		SecurityContextHolder.getContext().setAuthentication(null);
		return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
	}
	
	public ResponseEntity<?> signin(LoginRequest loginRequest) throws AuthenticationException {
		Authentication authentication = null;
		String jwt = null;
		UserDetailsImpl userDetails = null;
		UserResponse userResponse = null;
		try{
			authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("Bad Credentials, Try again");
		}


		SecurityContextHolder.getContext().setAuthentication(authentication);
		jwt = jwtUtils.generateJwtToken(authentication);

		userDetails = (UserDetailsImpl) authentication.getPrincipal();
		userResponse = UserResponse.userDetailsToUserResponse(userDetails);
		userResponse.setToken(jwt);

		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	public User getUser(Long id){
		return userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User not found"));
	}

	public ResponseEntity<?> findUserByEmail(String email){
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			UserProfile userProfile = new UserProfile();
			userProfile = UserProfile.userDetailsToUserProfile(user.get());
			emailService.sendEmail(userProfile);
			return new ResponseEntity<>(new MessageResponse("Email Exist"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new MessageResponse("No Email Exist"), HttpStatus.BAD_REQUEST);
	}
	
	public ResponseEntity<?> getProfile(UserDetailsImpl sessionUser) {
		User user = getUser(sessionUser.getId());
		UserProfile userProfile =  UserProfile.userDetailsToUserProfile(user);
		return new ResponseEntity<>(userProfile, HttpStatus.OK);
	}
	
	public void updateProfile(UserDetailsImpl sessionUser, UserProfile userProfile){

		User user = getUser(sessionUser.getId());

		if(user.getFirstName() == null || !user.getFirstName().equals(userProfile.getFirstName())){
			user.setFirstName(userProfile.getFirstName());
		}

		if(user.getMiddleName() == null || !user.getMiddleName().equals(userProfile.getMiddleName())){
			user.setMiddleName(userProfile.getMiddleName());
		}

		if(user.getLastName() == null || !user.getLastName().equals(userProfile.getLastName())){
			user.setLastName(userProfile.getLastName());
		}
		
		if(user.getEmail() == null || !user.getEmail().equals(userProfile.getEmail())) {
			user.setEmail(userProfile.getEmail());
		}
		
		if(user.getPhoneNumber() == null || !user.getPhoneNumber().equals(userProfile.getPhoneNumber())) {
			user.setPhoneNumber(userProfile.getPhoneNumber());
		}

		if(user.isShowPhoneNumber() != userProfile.isShowPhoneNumber()){
			user.setShowPhoneNumber(userProfile.isShowPhoneNumber());
		}

		if(user.isHelper() != userProfile.isHelper()){
			user.setHelper(userProfile.isHelper());
		}

		try{
			userRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException("Error Saving Profile" + e);
		}
	}
	
	public ResponseEntity<?> getAddressForUser(UserDetailsImpl sessionUser){
		UserAddress userAddress = null;
		User user = getUser(sessionUser.getId());
		Address address = user.getAddress();
		if(address == null){
			return new ResponseEntity<>(new MessageResponse("No Saved Address"), HttpStatus.OK);
		}
		userAddress = UserAddress.converterAddressToUserAddress(address);
		return new ResponseEntity<>(userAddress, HttpStatus.OK);
	}
	
	public void saveAddress(Long id, Address address) {
		User user = getUser(id);
		user.setAddress(address);
		userRepository.save(user);
	}
	
	public void saveOrUpdateAddress(UserDetailsImpl sessionUser, Address addressToBeUpdated) {
		User user = getUser(sessionUser.getId());
		Address userAddress = user.getAddress();
		if(userAddress == null){
			userAddress = new Address();
		}
		userAddress.setAddressLine1(addressToBeUpdated.getAddressLine1());
		userAddress.setAddressLine2(addressToBeUpdated.getAddressLine2());
		userAddress.setCity(addressToBeUpdated.getCity());
		userAddress.setState(addressToBeUpdated.getState());
		userAddress.setZipCode(addressToBeUpdated.getZipCode());
		userAddress.setUser(user);
		user.setAddress(userAddress);

		try{
			userRepository.save(user);
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new BadCredentialsException("Bad Data");
		}
	}
	
	public ResponseEntity<?> deleteAddress(Long id, Address addressToDelete) {
		User user = getUser(id);
		Address addresses = user.getAddress();
		//Address address = addresses.stream().filter(eachAddress -> eachAddress.getId() == addressToDelete.getId()).findFirst().get();
//		user.getUserAddress().remove(userAddress);
//		userRepository.save(user);
		userRepository.deleteByUserId(addressToDelete.getId());
		return ResponseEntity.ok(new MessageResponse("Success"));
	}

	public ResponseEntity<?> addOrUpdateProfilePicture(UserDetailsImpl sessionUser, String base64Image){
		User user = getUser(sessionUser.getId());
		byte[] imageData = base64Image.getBytes();
		user.setProfilePicture(imageData);

		try{
			userRepository.save(user);
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new BadCredentialsException("Bad Data");
		}
		return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);

	}

	public ResponseEntity<?> getAllHelpers(){
		List<UserProfileAndAddress> userProfileAndAddress = new ArrayList<>();
		userRepository.findAll().forEach(user -> userProfileAndAddress.add(UserProfileAndAddress.userToUserProfileAndAddress(user)));
		System.out.println(userProfileAndAddress);
		return new ResponseEntity<>(userProfileAndAddress,HttpStatus.OK);
	}

	public ResponseEntity<?> deleteProfilePicture(UserDetailsImpl userSession) {
		User user = getUser(userSession.getId());
		user.setProfilePicture(null);
		try{
			userRepository.save(user);
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new BadCredentialsException("Bad Data");
		}
		return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
	}
}
