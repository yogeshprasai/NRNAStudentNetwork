package org.nrna.services;

import org.nrna.dto.response.LoginResponse;
import org.nrna.services.exception.CustomGenericException;
import org.nrna.services.exception.ResourceNotFoundException;
import org.nrna.dto.response.UserAddress;
import org.nrna.dto.UserProfileAndAddress;
import org.nrna.dao.PasswordResetToken;
import org.nrna.dto.UserDetailsImpl;
import org.nrna.dto.UserProfile;
import org.nrna.dto.request.PasswordResetWithToken;
import org.nrna.repository.PasswordResetRepository;
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

import org.nrna.dao.User;
import org.nrna.dao.Address;
import org.nrna.dto.request.LoginRequest;
import org.nrna.dto.request.SignupRequest;
import org.nrna.dto.response.MessageResponse;
import org.nrna.repository.AddressRepository;
import org.nrna.repository.UserRepository;
import org.nrna.security.JwtUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AddressRepository addressRepository;

	@Autowired
	PasswordResetRepository passwordResetRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EmailService emailService;
	
	UserService(){
		
	}
	
	public ResponseEntity<?> signUp(SignupRequest signUpRequest) {
		try {
			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				return ResponseEntity.badRequest().body(new MessageResponse("EmailExist"));
			}

			User user = new User();
			user.setPassword(encoder.encode(signUpRequest.getPassword()));
			user.setEmail(signUpRequest.getEmail());
			userRepository.save(user);
		}catch (Exception e) {
			throw new CustomGenericException("Cannot Retrieve and Save User");
		}
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
		LoginResponse loginResponse = null;
		try{
			authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("Bad Credentials, Try again");
		}


		SecurityContextHolder.getContext().setAuthentication(authentication);
		jwt = jwtUtils.generateJwtToken(authentication);

		userDetails = (UserDetailsImpl) authentication.getPrincipal();
		loginResponse = LoginResponse.convertUserDetailsToUserResponse(userDetails);
		loginResponse.setToken(jwt);

		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}
	
	public User getUser(Long id){
		return userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User not found"));
	}

	public User getUserByEmail(String email){
		return userRepository.findByEmail(email)
				.orElseThrow(()-> new ResourceNotFoundException("User not found"));
	}

	public ResponseEntity<?> passwordResetRequest(String email){
		Optional<User> user = null;
		try{
			user = userRepository.findByEmail(email);
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new BadCredentialsException("Bad Data");
		}

		if (user.isPresent()) {
			UserProfile userProfile = UserProfile.userDetailsToUserProfile(user.get());
			String longToken = String.valueOf(Math.round(Math.random()*1000000));
			String shortToken = longToken.substring(0, 6);
			expireAllPreviousTokens(user.get());
			//For Local Testing
			//createPasswordResetTokenForUser(user.get(), "123456");
			createPasswordResetTokenForUser(user.get(), shortToken);

			try{
				emailService.sendEmail(userProfile, "password-reset", shortToken);
			}catch (Exception e){
				return new ResponseEntity<>(new MessageResponse("Error Sending Email"), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(new MessageResponse("Email Exist"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new MessageResponse("No Email Exist"), HttpStatus.BAD_REQUEST);
	}

	private void expireAllPreviousTokens(User user){
		try{
			List<PasswordResetToken> passwordResetTokens = passwordResetRepository.findByUserId(user.getId());
			for(PasswordResetToken passwordResetToken : passwordResetTokens){
				passwordResetToken.setExpired(true);
				passwordResetRepository.save(passwordResetToken);
			}
		}catch (Exception e) {
			throw new CustomGenericException("Cannot Expire Tokens");
		}
	}

	private void createPasswordResetTokenForUser(User user, String token) {
		LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);
		PasswordResetToken myToken = new PasswordResetToken(user, token, expiryDate);
		try{
			passwordResetRepository.save(myToken);
		}catch (Exception e) {
			throw new CustomGenericException("Cannot Save User");
		}
	}

	public ResponseEntity<?> passwordResetWithToken(PasswordResetWithToken passwordResetWithToken) {
		Optional<User> user = userRepository.findByEmail(passwordResetWithToken.getEmail());
		if (user.isPresent()) {
			User newUserToBeUpdated = user.get();
			if(verifyToken(newUserToBeUpdated, passwordResetWithToken)){
				try{
					newUserToBeUpdated.setPassword(encoder.encode(passwordResetWithToken.getPassword()));
					userRepository.save(newUserToBeUpdated);
				} catch (Exception e) {
					throw new CustomGenericException("Cannot Save User");
				}
				return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(new MessageResponse("Password Reset Failed"), HttpStatus.BAD_REQUEST);
	}

	private boolean verifyToken(User user, PasswordResetWithToken passwordResetWithToken){
		Optional<PasswordResetToken> savedUnExpiredPasswordToken = null;
		try{
			savedUnExpiredPasswordToken = passwordResetRepository.findByUserId(user.getId())
					.stream()
					.filter(val -> !val.isExpired()).findFirst();
		}catch (Exception e) {
			throw new ResourceNotFoundException("Cannot Find User");
		}

		if(savedUnExpiredPasswordToken.isPresent()){
            return savedUnExpiredPasswordToken.get().getToken().equals(passwordResetWithToken.getToken());
        }
		return false;
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

		if(user.isStudent() != userProfile.isStudent()){
			user.setStudent(userProfile.isStudent());
		}

		if(null == user.getUniversity() || !user.getUniversity().equals(userProfile.getUniversity())){
			user.setUniversity(userProfile.getUniversity());
		}

		if(user.isApplyForVolunteer() != userProfile.isApplyForVolunteer()){
			user.setApplyForVolunteer(userProfile.isApplyForVolunteer());
		}

		if(user.isVolunteer() != userProfile.isVolunteer()){
			user.setVolunteer(userProfile.isVolunteer());
		}

		try{
			userRepository.save(user);
		} catch (Exception e) {
			throw new CustomGenericException("Error Saving User " + e);
		}
	}

	public void updateProfileForVolunteer(UserProfile userProfile){
		User user = getUserByEmail(userProfile.getEmail());
		user.setVolunteer(true);
		user.setApplyForVolunteer(false);

		try{
			userRepository.save(user);
		} catch (Exception e) {
			throw new CustomGenericException("Error Saving User " + e);
		}

	}
	
	public ResponseEntity<?> getAddressForUser(UserDetailsImpl sessionUser){
		UserAddress userAddress = null;
		User user = getUser(sessionUser.getId());
		Address address = user.getAddress();
		if(address == null){
			return new ResponseEntity<>(new MessageResponse("No Saved Address"), HttpStatus.OK);
		}
		userAddress = UserAddress.convertAddressToUserAddress(address);
		return new ResponseEntity<>(userAddress, HttpStatus.OK);
	}
	
	public void saveAddress(Long id, Address address) {
		User user = getUser(id);
		user.setAddress(address);
		try{
			userRepository.save(user);
		}catch (Exception e) {
			throw new CustomGenericException("Error Saving User " + e);
		}

	}
	
	public void saveOrUpdateAddress(UserDetailsImpl sessionUser, Address addressToBeUpdated) {
		User user = getUser(sessionUser.getId());
		Address userAddress = user.getAddress();
		if(userAddress == null){
			userAddress = new Address();
		}
		userAddress.setCity(addressToBeUpdated.getCity());
		userAddress.setState(addressToBeUpdated.getState());
		userAddress.setZipCode(addressToBeUpdated.getZipCode());
		userAddress.setUser(user);
		user.setAddress(userAddress);

		try{
			userRepository.save(user);
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new CustomGenericException("Bad Data");
		}
	}
	
	public ResponseEntity<?> deleteAddress(Long id, Address addressToDelete) {
		try{
			userRepository.deleteByUserId(addressToDelete.getId());
			return ResponseEntity.ok(new MessageResponse("Success"));
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new CustomGenericException("Error Deletting User ");
		}
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

	public ResponseEntity<?> getAllVolunteers(){
		List<User> allUsers = userRepository.findAllUsers();
		List<UserProfileAndAddress> userProfileAndAddresses = allUsers.stream()
				.filter(User::isVolunteer)
				.map(p -> new UserProfileAndAddress(
						p.getFirstName(),
						p.getMiddleName(),
						p.getLastName(),
						p.getEmail(),
						p.getPhoneNumber(),
						p.isShowPhoneNumber(),
						p.getUniversity(),
						new String(p.getProfilePicture()),
						new UserAddress(p.getAddress().getCity(), p.getAddress().getState(), p.getAddress().getZipCode())
				))
				.collect(Collectors.toList());


		//allUsers.forEach(user -> userProfileAndAddress.add(UserProfileAndAddress.userToUserProfileAndAddress(user)));
		System.out.println(userProfileAndAddresses);
		return new ResponseEntity<>(userProfileAndAddresses,HttpStatus.OK);
	}

	public ResponseEntity<?> getAllApplyForVolunteerRequest(){
		List<User> allUsers = userRepository.findAllUsers();
		List<UserProfileAndAddress> userProfileAndAddresses = allUsers.stream()
				.filter(User::isApplyForVolunteer)
				.map(p -> new UserProfileAndAddress(
						p.getFirstName(),
						p.getMiddleName(),
						p.getLastName(),
						p.getEmail(),
						p.getPhoneNumber(),
						p.isShowPhoneNumber(),
						p.getUniversity(),
						new String(p.getProfilePicture()),
						new UserAddress(p.getAddress().getCity(), p.getAddress().getState(), p.getAddress().getZipCode())
				))
				.collect(Collectors.toList());


		//allUsers.forEach(user -> userProfileAndAddress.add(UserProfileAndAddress.userToUserProfileAndAddress(user)));
		System.out.println(userProfileAndAddresses);
		return new ResponseEntity<>(userProfileAndAddresses,HttpStatus.OK);
	}

	public ResponseEntity<?> getAllStudents(){

		List<User> allUsers = userRepository.findAllUsers();
		List<UserProfileAndAddress> userProfileAndAddress = allUsers.stream()
				.filter(User::isStudent)
				.map(user -> new UserProfileAndAddress(
						user.getFirstName(),
						user.getMiddleName(),
						user.getLastName(),
						user.getEmail(),
						user.getPhoneNumber(),
						user.isShowPhoneNumber(),
						user.getUniversity(),
						new String(user.getProfilePicture()),
						new UserAddress(user.getAddress().getCity(), user.getAddress().getState(), user.getAddress().getZipCode())
				))
				.collect(Collectors.toList());
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
