package org.nrna.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nrna.payload.request.UserProfile;
import org.nrna.payload.response.MessageResponse;
import org.nrna.payload.response.UserResponse;
import org.nrna.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.nrna.payload.request.LoginRequest;
import org.nrna.payload.request.SignupRequest;
import org.nrna.security.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	UserService userService;

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		UserResponse userResponse = userService.signin(loginRequest);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.signUp(signUpRequest);
	}

	@PostMapping("/profile")
	public ResponseEntity<?> updateProfile(@Valid @RequestBody UserProfile userProfile) {
		UserDetailsImpl user = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user.getId() != null){
			userService.updateProfile(user.getId(), userProfile);
			return ResponseEntity.ok(new MessageResponse("Success"));
		}
		return ResponseEntity.ok(new MessageResponse("Error"));
	}
	
}
