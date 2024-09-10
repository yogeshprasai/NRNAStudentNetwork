package org.nrna.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nrna.models.UserProfile;
import org.nrna.models.dto.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.nrna.models.dto.Address;
import org.nrna.models.response.MessageResponse;
import org.nrna.security.services.UserService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class ProfileController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/profile")
	public ResponseEntity<?> getProfile() {
		UserDetailsImpl user = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.getProfile(user.getId());
	}
	
	@PostMapping("/profile")
	public ResponseEntity<?> updateProfile(HttpServletRequest request, @Valid @RequestBody UserProfile userProfile) {
		UserDetailsImpl user = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user.getId() != null){
			userService.updateProfile(user.getId(), userProfile);
			if(user.getEmail().equals(userProfile.getEmail())){
				return ResponseEntity.ok(new MessageResponse("Success"));
			}
			return ResponseEntity.ok(new MessageResponse("Success but Logout User"));
		}
		return null;
	}
	
	@PostMapping("/address")
	public ResponseEntity<?> saveOrUpdateAddress(HttpServletRequest request, @Valid @RequestBody Address userAddress) {
		UserDetailsImpl user = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user.getId() != null){
			userService.saveOrUpdateAddress(user.getId(), userAddress);
			return ResponseEntity.ok(new MessageResponse("Success: User Address Updated."));
		}
		return null;
	}

	@GetMapping("/address")
	public ResponseEntity<?> getUserAddress() {
		UserDetailsImpl user = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(user.getId() != null){
			userService.getAddressForUser(user.getId());
		}
		//UserAddress userAddress = userAddress.userAddressRequestToUserAddress(user, userAddress);
//		userService.saveAddress(id, address);
		return ResponseEntity.ok(new MessageResponse("Success"));
	}
	
	@PutMapping("/profile/address_delete")
	public ResponseEntity<?> deleteAddress(HttpServletRequest request, @Valid @RequestBody Address address) {
		HttpSession session = request.getSession();
		Long id = (Long) session.getAttribute("id");
		return userService.deleteAddress(id, address);
	}

}
