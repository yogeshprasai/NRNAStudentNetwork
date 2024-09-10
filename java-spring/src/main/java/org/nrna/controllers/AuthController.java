package org.nrna.controllers;

import javax.validation.Valid;

import org.nrna.models.response.MessageResponse;
import org.nrna.models.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import org.nrna.models.request.LoginRequest;
import org.nrna.models.request.SignupRequest;
import org.nrna.security.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.signUp(signUpRequest);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		UserResponse userResponse = userService.signin(loginRequest);
		return new ResponseEntity<>(userResponse, HttpStatus.OK);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		MessageResponse messageResponse = userService.logout();
		return new ResponseEntity<>(messageResponse, HttpStatus.OK);
	}
	
}
