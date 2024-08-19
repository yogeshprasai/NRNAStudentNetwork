package org.nrna.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nrna.payload.request.UserProfile;
import org.nrna.security.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.nrna.models.User;
import org.nrna.models.UserAddress;
import org.nrna.payload.response.MessageResponse;
import org.nrna.security.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
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
	
	@PostMapping("/profile")
	public ResponseEntity<?> updateProfile(HttpServletRequest request, @Valid @RequestBody UserProfile userProfile) {
		UserDetailsImpl user = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user.getId() != null){
			userService.updateProfile(user.getId(), userProfile);
			return ResponseEntity.ok(new MessageResponse("Success"));
		}
		return ResponseEntity.ok(new MessageResponse("Error"));
	}
	
	@PostMapping("/profile/address")
	public ResponseEntity<?> addNewAddress(HttpServletRequest request, @Valid @RequestBody UserAddress userAddress) {
		System.out.println("userRequest");
		//User user = userService.getUser(userAddress.getId());
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("id");
		//UserAddress userAddress = userAddress.userAddressRequestToUserAddress(user, userAddress);
		userService.saveAddress(id, userAddress);
		return ResponseEntity.ok(new MessageResponse("Success"));
	}
	
	
	@PutMapping("/profile/address")
	public ResponseEntity<?> editAddress(HttpServletRequest request, @Valid @RequestBody UserAddress userAddress) {
		System.out.println("userRequest");
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("id");
		userService.editAddress(id, userAddress);
		
		return ResponseEntity.ok(new MessageResponse("Success"));
	}
	
	@PutMapping("/profile/address_delete")
	public ResponseEntity<?> deleteAddress(HttpServletRequest request, @Valid @RequestBody UserAddress userAddress) {
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("id");
		return userService.deleteAddress(id, userAddress);
	}

}
