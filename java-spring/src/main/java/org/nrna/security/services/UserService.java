package org.nrna.security.services;

import java.util.List;

import org.nrna.payload.request.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.nrna.models.User;
import org.nrna.models.UserAddress;
import org.nrna.payload.request.LoginRequest;
import org.nrna.payload.request.SignupRequest;
import org.nrna.payload.response.MessageResponse;
import org.nrna.payload.response.UserResponse;
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
	
	public UserResponse signin(LoginRequest loginRequest) {
		Authentication authentication = null;
		String jwt = null;
		UserDetailsImpl userDetails = null;

		authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		jwt = jwtUtils.generateJwtToken(authentication);

		userDetails = (UserDetailsImpl) authentication.getPrincipal();

		return UserResponse.userDetailsToUserResponse(jwt, userDetails);
	}
	
	public User getUser(Long id){
		User user = userRepository.findById(id).get();
		return user;
	}
	
	public ResponseEntity<?> getProfile(Long id) {
		User user = getUser(id);
		//UserResponse userResp =  UserResponse.userToUserResponse(user);
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	public void updateProfile(Long id, UserProfile userProfile){

		User user = getUser(id);
			
//		if(user.getPassword() != null) {
//			user.setPassword(encoder.encode(userRequest.getPassword()));
//		}

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
		
		userRepository.save(user);
	}
	
	public UserAddress getAddressForUser(Long id){
		UserAddress userAdd = addressRepository.getOne(id);
		return userAdd;
	}
	
	public void saveAddress(Long id, UserAddress address) {
		User user = getUser(id);
		user.getUserAddress().add(address);
		userRepository.save(user);
	}
	
	public void editAddress(Long id, UserAddress addressToBeUpdated) {
		User user = getUser(id);
		List<UserAddress> userAddresses = user.getUserAddress();
		UserAddress userAddress = userAddresses.stream().filter(address -> address.getId() == addressToBeUpdated.getId()).findFirst().get();
		userAddress.setName(addressToBeUpdated.getName());
		userAddress.setAddLine1(addressToBeUpdated.getAddLine1());
		userAddress.setCity(addressToBeUpdated.getCity());
		userAddress.setState(addressToBeUpdated.getState());
		userAddress.setZipCode(addressToBeUpdated.getZipCode());
		user.getUserAddress().add(userAddress);
		userRepository.save(user);
	}
	
	public ResponseEntity<?> deleteAddress(Long id, UserAddress addressToDelete) {
		User user = getUser(id);
		List<UserAddress> userAddresses = user.getUserAddress();
		UserAddress userAddress = userAddresses.stream().filter(address -> address.getId() == addressToDelete.getId()).findFirst().get();
//		user.getUserAddress().remove(userAddress);
//		userRepository.save(user);
		userRepository.deleteByUserId(addressToDelete.getId());
		return ResponseEntity.ok(new MessageResponse("Success"));
	}
}
