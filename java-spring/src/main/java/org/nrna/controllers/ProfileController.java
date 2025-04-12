package org.nrna.controllers;

import javax.mail.Multipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.nrna.dto.UserProfile;
import org.nrna.dto.UserDetailsImpl;
import org.nrna.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import org.nrna.dao.Address;
import org.nrna.dto.response.MessageResponse;
import org.nrna.services.UserService;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);
	
	@Autowired
	UserService userService;

    @Autowired
    private UserRepository userRepository;

	@GetMapping("/profile")
	public ResponseEntity<?> getProfile() {
		UserDetailsImpl user = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.getProfile(user);
	}
	
	@PostMapping("/profile")
	public ResponseEntity<?> updateProfile(HttpServletRequest request, @Valid @RequestBody UserProfile userProfile) {
		UserDetailsImpl sessionUser = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userService.updateProfile(sessionUser, userProfile);
		if(sessionUser.getEmail().equals(userProfile.getEmail())){
			return ResponseEntity.ok(new MessageResponse("Success"));
		}
		return ResponseEntity.ok(new MessageResponse("Success but Logout User"));
	}

	/*
		Only admin can approve volunteer
	 */
	@PostMapping("/profileApprovalByAdmin")
	public ResponseEntity<?> updateVolunteerRequest(HttpServletRequest request, @Valid @RequestBody UserProfile userProfile) {
		userService.updateProfileForVolunteer(userProfile);
		return ResponseEntity.ok(new MessageResponse("Success"));
	}

	@PostMapping("/saveOrUpdateProfilePic")
	public ResponseEntity<?> saveOrUpdateProfilePic(@RequestParam("image") String base64Image) throws IOException {
		logger.info("base64Image" + base64Image);
		UserDetailsImpl sessionUser = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.addOrUpdateProfilePicture(sessionUser, base64Image);
		//return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
	}

	@PutMapping("/deleteProfilePicture")
	public ResponseEntity<?> deleteProfilePicture() {
		UserDetailsImpl sessionUser = (UserDetailsImpl)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userService.deleteProfilePicture(sessionUser);
	}

}
