package org.nrna.security.services;

import java.util.List;

import org.nrna.models.dto.UserDetailsImpl;
import org.nrna.models.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
	
	UserService(){
		
	}
	
	public ResponseEntity<?> signUp(SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("EmailExist"));
		}

		User user = new User();
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		user.setEmail(signUpRequest.getEmail());
		user.setRole("Student");
		userRepository.save(user);

		return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
	}

	public MessageResponse logout(){
		SecurityContextHolder.getContext().setAuthentication(null);
		return new MessageResponse("Logout Successful");
	}
	
	public UserResponse signin(LoginRequest loginRequest) {
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

		return userResponse;
	}
	
	public User getUser(Long id){
		User user = userRepository.findById(id).get();
		return user;
	}
	
	public ResponseEntity<?> getProfile(Long id) {
		User user = getUser(id);
		UserProfile userProfile = UserProfile.userDetailsToUserProfile(user);
		return new ResponseEntity<>(userProfile, HttpStatus.OK);
	}
	
	public void updateProfile(Long id, UserProfile userProfile){

		User user = getUser(id);

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
		try{
			userRepository.save(user);
		} catch (Exception e) {
			throw new RuntimeException("Error Saving Profile" + e);
		}

	}
	
	public Address getAddressForUser(Long id){
		Address userAddress = addressRepository.getOne(id);
		addressRepository.getOne(id);
		return userAddress;
	}
	
	public void saveAddress(Long id, Address address) {
		User user = getUser(id);
		user.getUserAddress().add(address);
		userRepository.save(user);
	}
	
	public void saveOrUpdateAddress(Long id, Address addressToBeUpdated) {
		User user = getUser(id);
		Address userAddress = new Address();
		userAddress.setAddressLine1(addressToBeUpdated.getAddressLine1());
		userAddress.setAddressLine2(addressToBeUpdated.getAddressLine2());
		userAddress.setCity(addressToBeUpdated.getCity());
		userAddress.setState(addressToBeUpdated.getState());
		userAddress.setZipCode(addressToBeUpdated.getZipCode());
		userAddress.setUser(user);
		user.getUserAddress().add(userAddress);
		try{
			userRepository.save(user);
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new BadCredentialsException("Bad Data");
		}

	}
	
	public ResponseEntity<?> deleteAddress(Long id, Address addressToDelete) {
		User user = getUser(id);
		List<Address> addresses = user.getUserAddress();
		Address address = addresses.stream().filter(eachAddress -> eachAddress.getId() == addressToDelete.getId()).findFirst().get();
//		user.getUserAddress().remove(userAddress);
//		userRepository.save(user);
		userRepository.deleteByUserId(addressToDelete.getId());
		return ResponseEntity.ok(new MessageResponse("Success"));
	}
}
