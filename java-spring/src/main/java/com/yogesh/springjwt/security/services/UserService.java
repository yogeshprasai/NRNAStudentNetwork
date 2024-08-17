package com.yogesh.springjwt.security.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import com.yogesh.springjwt.models.User;
import com.yogesh.springjwt.models.UserAddress;
import com.yogesh.springjwt.payload.request.LoginRequest;
import com.yogesh.springjwt.payload.request.SignupRequest;
import com.yogesh.springjwt.payload.request.UserAddressRequest;
import com.yogesh.springjwt.payload.request.UserRequest;
import com.yogesh.springjwt.payload.response.MessageResponse;
import com.yogesh.springjwt.payload.response.UserResponse;
import com.yogesh.springjwt.repository.AddressRepository;
import com.yogesh.springjwt.repository.UserRepository;
import com.yogesh.springjwt.security.jwt.JwtUtils;

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
	
	public ResponseEntity<?> signin(LoginRequest loginRequest) {
		Authentication authentication = null;
		String jwt = null;
		UserDetailsImpl userDetails = null;

		authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		jwt = jwtUtils.generateJwtToken(authentication);

		userDetails = (UserDetailsImpl) authentication.getPrincipal();

		UserResponse userResponse = UserResponse.userDetailsToUserResponse(jwt, userDetails);

		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}
	
	public User getUser(Long id){
		User user = userRepository.findById(id).get();
		return user;
	}
	
	public ResponseEntity<?> getProfile(Long id) {
		User user = getUser(id);
		UserResponse userResp =  UserResponse.userToUserResponse(user);
		return new ResponseEntity<>(userResp, HttpStatus.OK);
	}
	
	public void updateProfile(User user, UserRequest userRequest){
			
		if(userRequest.getPassword() != null) {
			user.setPassword(encoder.encode(userRequest.getPassword()));
		}
		
		if(userRequest.getAge() != user.getAge()) {
			user.setAge(userRequest.getAge());
		}
		
		if(userRequest.getDob()!= user.getDob()) {
			user.setDob(userRequest.getDob());
		}
		
		if(userRequest.getEmail() != user.getEmail()) {
			user.setEmail(userRequest.getEmail());
		}
		
		if(userRequest.getGender() != user.getGender()) {
			user.setGender(userRequest.getGender());
		}
		
		if(userRequest.getName() != user.getName()) {
			user.setName(userRequest.getName());
		}
		
		if(userRequest.getPhoneNumber() != user.getPhoneNumber()) {
			user.setPhoneNumber(userRequest.getPhoneNumber());
		}
		
		userRepository.save(user);
	}
	
	public UserAddress getAddressForUser(Long id){
		UserAddress userAdd = addressRepository.getOne(id);
		return userAdd;
	}
	
	public void saveAddress(User user, UserAddress address) {
		user.getUserAddress().add(address);
		userRepository.save(user);
	}
	
	public void editAddress(UserAddressRequest addressToBeUpdated) {
		User user = getUser(addressToBeUpdated.getUserId());
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
	
	public ResponseEntity<?> deleteAddress(UserAddressRequest addressToDelete) {
		User user = getUser(addressToDelete.getUserId());
		List<UserAddress> userAddresses = user.getUserAddress();
		UserAddress userAddress = userAddresses.stream().filter(address -> address.getId() == addressToDelete.getId()).findFirst().get();
//		user.getUserAddress().remove(userAddress);
//		userRepository.save(user);
		userRepository.deleteByUserId(addressToDelete.getId());
		return ResponseEntity.ok(new MessageResponse("Success"));
	}
}
