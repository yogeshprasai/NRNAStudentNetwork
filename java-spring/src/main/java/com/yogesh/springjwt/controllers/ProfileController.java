package com.yogesh.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.springjwt.models.ERole;
import com.yogesh.springjwt.models.Role;
import com.yogesh.springjwt.models.User;
import com.yogesh.springjwt.models.UserAddress;
import com.yogesh.springjwt.payload.request.LoginRequest;
import com.yogesh.springjwt.payload.request.SignupRequest;
import com.yogesh.springjwt.payload.request.UserAddressRequest;
import com.yogesh.springjwt.payload.request.UserRequest;
import com.yogesh.springjwt.payload.response.MessageResponse;
import com.yogesh.springjwt.repository.RoleRepository;
import com.yogesh.springjwt.repository.UserRepository;
import com.yogesh.springjwt.security.jwt.JwtUtils;
import com.yogesh.springjwt.security.services.UserDetailsImpl;
import com.yogesh.springjwt.security.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class ProfileController {
	
	@Autowired
	UserService userService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ResponseEntity<?> helloUser() {
		return ResponseEntity.ok(new MessageResponse("Hello Profile!"));
	}
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile(@Valid @RequestParam int id) {
		
		return userService.getProfile(Long.valueOf(id));
	}
	
	@PutMapping("/profile")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody UserRequest userRequest) {
		User user = userService.getUser(userRequest.getId());
		userService.updateProfile(user, userRequest);
		
		return ResponseEntity.ok(new MessageResponse("Success"));
	}
	
	@PostMapping("/profile/address")
	public ResponseEntity<?> addNewAddress(@Valid @RequestBody UserAddressRequest userAddressRequest) {
		System.out.println("userRequest");
		User user = userService.getUser(userAddressRequest.getUserId());
		UserAddress userAddress = userAddressRequest.userAddressRequestToUserAddress(user, userAddressRequest);
		userService.saveAddress(user, userAddress);
		return ResponseEntity.ok(new MessageResponse("Success"));
	}
	
	
	@PutMapping("/profile/address")
	public ResponseEntity<?> editAddress(@Valid @RequestBody UserAddressRequest userAddressRequest) {
		System.out.println("userRequest");
		userService.editAddress(userAddressRequest);
		
		return ResponseEntity.ok(new MessageResponse("Success"));
	}
	
	@PutMapping("/profile/address_delete")
	public ResponseEntity<?> deleteAddress(@Valid @RequestBody UserAddressRequest userAddressRequest) {
		return userService.deleteAddress(userAddressRequest);
	}

}
