package org.nrna.controllers;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.nrna.models.dto.UserDetailsImpl;
import org.nrna.models.response.MessageResponse;
import org.nrna.models.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.nrna.models.request.LoginRequest;
import org.nrna.models.request.SignupRequest;
import org.nrna.security.services.UserService;

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
		return userService.signin(loginRequest);
	}

	@PostMapping("/logout")
	public ResponseEntity<?> logout() {
		return userService.logout();
	}
	
}
