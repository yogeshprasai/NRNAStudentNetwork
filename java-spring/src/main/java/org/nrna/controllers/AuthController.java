package org.nrna.controllers;

import javax.validation.Valid;

import org.nrna.dto.UserDetailsImpl;
import org.nrna.dto.request.EmailExist;
import org.nrna.dto.request.PasswordResetWithToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.nrna.dto.request.LoginRequest;
import org.nrna.dto.request.SignupRequest;
import org.nrna.services.UserService;

import java.util.HashMap;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	UserService userService;

	@GetMapping("/getSession")
	public ResponseEntity<?> getSession() {
		UserDetailsImpl sessionUser = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(sessionUser != null) {
			return new ResponseEntity<>(sessionUser.getId(), HttpStatus.OK);
		}
		HashMap<String, String> noSessionObject = new HashMap<>();
		noSessionObject.put("message", "No Session Active");
		noSessionObject.put("action", "logout");
		return new ResponseEntity<>(noSessionObject, HttpStatus.OK);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		return userService.signUp(signUpRequest);
	}

	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		ResponseEntity<?> hello = userService.signin(loginRequest);
			return hello;
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		return userService.logout();
	}

	@PostMapping("/passwordResetRequest")
	public ResponseEntity<?> passwordResetRequest(@Valid @RequestBody EmailExist emailExist) {
		return userService.passwordResetRequest(emailExist.getEmail());
	}

	@PostMapping("/passwordResetWithToken")
	public ResponseEntity<?> passwordResetWithToken(@Valid @RequestBody PasswordResetWithToken passwordResetWithToken) {
		return userService.passwordResetWithToken(passwordResetWithToken);
	}
	
}
