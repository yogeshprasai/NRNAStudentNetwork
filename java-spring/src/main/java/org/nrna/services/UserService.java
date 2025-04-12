package org.nrna.services;

import org.nrna.dto.request.CreateNewPassword;
import org.nrna.dto.response.LoginResponse;
import org.nrna.repository.AddressRepository;
import org.nrna.services.exception.CustomGenericException;
import org.nrna.services.exception.ResourceNotFoundException;
import org.nrna.dto.response.UserAddress;
import org.nrna.dto.UserProfileAndAddress;
import org.nrna.dao.PasswordResetToken;
import org.nrna.dto.UserDetailsImpl;
import org.nrna.dto.UserProfile;
import org.nrna.dto.request.VerifyToken;
import org.nrna.repository.PasswordResetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.nrna.dao.User;
import org.nrna.dao.Address;
import org.nrna.dto.request.LoginRequest;
import org.nrna.dto.request.SignupRequest;
import org.nrna.dto.response.MessageResponse;
import org.nrna.repository.UserRepository;
import org.nrna.security.JwtUtils;

import javax.mail.SendFailedException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class) ;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	UserRepository userRepository;

	@Autowired
	PasswordResetRepository passwordResetRepository;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	EmailService emailService;
    @Autowired
    private AddressRepository addressRepository;

	UserService(){
		
	}
	
	public ResponseEntity<?> signUp(SignupRequest signUpRequest) {
		try {
			if (userRepository.existsByEmail(signUpRequest.getEmail())) {
				throw new BadCredentialsException("Email already in use");
			}

			User user = new User();
			user.setPassword(encoder.encode(signUpRequest.getPassword()));
			user.setEmail(signUpRequest.getEmail());
			userRepository.save(user);
		}catch (BadCredentialsException ex){
			throw new BadCredentialsException("Email already in use");
		}
		return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
	}

	public ResponseEntity<?> logout(){
		SecurityContextHolder.getContext().setAuthentication(null);
		return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
	}
	
	public ResponseEntity<?> signin(LoginRequest loginRequest) throws BadCredentialsException {
		Authentication authentication = null;
		String jwt = null;
		UserDetailsImpl userDetails = null;
		LoginResponse loginResponse = null;
		try{
			authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
		} catch (BadCredentialsException ex) {
			throw new BadCredentialsException("Bad Credentials, Try again");
		}


		SecurityContextHolder.getContext().setAuthentication(authentication);
		jwt = jwtUtils.generateJwtToken(authentication);

		userDetails = (UserDetailsImpl) authentication.getPrincipal();
		loginResponse = LoginResponse.convertUserDetailsToUserResponse(userDetails);
		loginResponse.setToken(jwt);

		return new ResponseEntity<>(loginResponse, HttpStatus.OK);
	}
	
	public User getUser(Long id){
		return userRepository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User not found"));
	}

	public User getUserByEmail(String email){
		return userRepository.findByEmail(email)
				.orElseThrow(()-> new ResourceNotFoundException("User not found"));
	}

	public ResponseEntity<?> sendTokenEmailToResetPassword(String email) throws SendFailedException {
		Optional<User> user = null;
		try{
			user = userRepository.findByEmail(email);
		}catch (Exception e){
			logger.error("ErrorMessage: " + e.getMessage());
			throw new BadCredentialsException("User not found");
		}

		if (user.isPresent()) {
			UserProfile userProfile = UserProfile.userDetailsToUserProfile(user.get());
			String longToken = String.valueOf(Math.round(Math.random()*1000000));
			String shortToken = longToken.substring(0, 6);
			expireAllPreviousTokens(user.get());
			createPasswordResetTokenForUser(user.get(), shortToken);

			emailService.sendEmail(userProfile, "password-reset", shortToken);

			return new ResponseEntity<>(new MessageResponse("Email Exist"), HttpStatus.OK);
		}
		logger.info("Send Token Email To Reset Password");
		throw new BadCredentialsException("User not found");
	}

	private void expireAllPreviousTokens(User user){
		try{
			List<PasswordResetToken> passwordResetTokens = passwordResetRepository.findByUserId(user.getId());
			for(PasswordResetToken passwordResetToken : passwordResetTokens){
				passwordResetToken.setExpired(true);
				passwordResetRepository.save(passwordResetToken);
			}
		}catch (Exception e) {
			logger.error("Expire All Previous Tokens Error : " + e.getMessage());
			throw new CustomGenericException("Cannot Expire Tokens");
		}
	}

	private void createPasswordResetTokenForUser(User user, String token) {
		LocalDateTime expiryDate = LocalDateTime.now().plusMinutes(30);
		PasswordResetToken myToken = new PasswordResetToken(user, token, expiryDate);
		try{
			passwordResetRepository.save(myToken);
		}catch (Exception e) {
			logger.error("Cannot Save Tokens Error : " + e.getMessage());
			throw new CustomGenericException("Cannot Save Token");
		}
	}

	public ResponseEntity<?> verifyTokenToResetPassword(VerifyToken verifyToken) {
		Optional<User> user = null;
		try{
			user = userRepository.findByEmail(verifyToken.getEmail());
			if (user.isPresent()) {
				User newUserToBeUpdated = user.get();
				if(verifyToken(newUserToBeUpdated, verifyToken)){
					return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
				}
				throw new CustomGenericException("Invalid Token");
			}
			throw new ResourceNotFoundException("User Not Found");
		}catch (ResourceNotFoundException ex) {
			logger.error("User Not Found : " + ex.getMessage());
			throw new CustomGenericException(ex.getMessage());
		}catch (CustomGenericException ex) {
			logger.error("Invalid Token Error : " + ex.getMessage());
			throw new CustomGenericException(ex.getMessage());
		}catch (Exception ex) {
			logger.error("Verify Token Error : " + ex.getMessage());
			throw new CustomGenericException(ex.getMessage());
		}
	}

	public ResponseEntity<?> newPasswordToResetPassword(CreateNewPassword createNewPassword) {
		Optional<User> user = null;
		try{
			user = userRepository.findByEmail(createNewPassword.getEmail());
		} catch (Exception ex) {
			throw new CustomGenericException(ex.getMessage());
		}

		if (user.isPresent()) {
			User newUserToBeUpdated = user.get();
				try{
					newUserToBeUpdated.setPassword(encoder.encode(createNewPassword.getPassword()));
					expireAllPreviousTokens(newUserToBeUpdated);
					userRepository.save(newUserToBeUpdated);
				} catch (Exception e) {
					throw new CustomGenericException("Password Not Saved");
				}
				return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
		}
		throw new CustomGenericException("User doesn't exist");
	}

	private boolean verifyToken(User user, VerifyToken passwordResetWithToken){
		Optional<PasswordResetToken> savedUnExpiredPasswordToken = null;
		try{
			savedUnExpiredPasswordToken = passwordResetRepository.findByUserId(user.getId())
					.stream()
					.filter(val -> !val.isExpired()).findFirst();
		}catch (Exception e) {
			throw new ResourceNotFoundException("Cannot Find User");
		}

		if(savedUnExpiredPasswordToken.isPresent()){
            return savedUnExpiredPasswordToken.get().getToken().equals(passwordResetWithToken.getToken());
        }
		return false;
	}
	
	public ResponseEntity<?> getProfile(UserDetailsImpl sessionUser) {
		User user = getUser(sessionUser.getId());
		UserProfile userProfile =  UserProfile.userDetailsToUserProfile(user);
		logger.info(userProfile.toString());
		return new ResponseEntity<>(userProfile, HttpStatus.OK);
	}
	
	public void updateProfile(UserDetailsImpl sessionUser, UserProfile userProfile){

		User user = getUser(sessionUser.getId());

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

		if(user.getAddress().getCity() == null || !user.getAddress().getCity().equals(userProfile.getCity())) {
			user.getAddress().setCity(userProfile.getCity());
		}

		if(user.getAddress().getState() == null || !user.getAddress().getState().equals(userProfile.getState())) {
			user.getAddress().setState(userProfile.getState());
		}

		if(user.getAddress().getZipCode() == null || !user.getAddress().getZipCode().equals(userProfile.getZipCode())) {
			user.getAddress().setZipCode(userProfile.getZipCode());
		}

		if(user.isShowPhoneNumber() != userProfile.isShowPhoneNumber()){
			user.setShowPhoneNumber(userProfile.isShowPhoneNumber());
		}

		if(user.isStudent() != userProfile.isStudent()){
			user.setStudent(userProfile.isStudent());
		}

		if(null == user.getUniversity() || !user.getUniversity().equals(userProfile.getUniversity())){
			user.setUniversity(userProfile.getUniversity());
		}

		if(user.isApplyForVolunteer() != userProfile.isApplyForVolunteer()){
			user.setApplyForVolunteer(userProfile.isApplyForVolunteer());
		}

		if(user.isVolunteer() != userProfile.isVolunteer()){
			user.setVolunteer(userProfile.isVolunteer());
		}

		try{
			logger.info("User to be updated " + user);
			user.getAddress().setUser(user);
			userRepository.save(user);
		} catch (Exception e) {
			throw new CustomGenericException("Error Saving User " + e);
		}
	}

	public void updateProfileForVolunteer(UserProfile userProfile){
		User user = getUserByEmail(userProfile.getEmail());
		user.setVolunteer(true);
		user.setApplyForVolunteer(false);

		try{
			logger.info("User set to be volunteer: " + user);
			userRepository.save(user);
		} catch (Exception e) {
			throw new CustomGenericException("Error Saving User " + e);
		}

	}
	
	public ResponseEntity<?> getAddressForUser(UserDetailsImpl sessionUser){
		UserAddress userAddress = null;
		User user = getUser(sessionUser.getId());
		Address address = user.getAddress();
		if(address == null){
			return new ResponseEntity<>(new MessageResponse("No Saved Address"), HttpStatus.OK);
		}
		userAddress = UserAddress.convertAddressToUserAddress(address);
		return new ResponseEntity<>(userAddress, HttpStatus.OK);
	}
	
	public void saveAddress(Long id, Address address) {
		User user = getUser(id);
		user.setAddress(address);
		try{
			userRepository.save(user);
		}catch (Exception e) {
			throw new CustomGenericException("Error Saving User " + e);
		}

	}
	
	public void saveOrUpdateAddress(UserDetailsImpl sessionUser, Address addressToBeUpdated) {
		User user = getUser(sessionUser.getId());
		Address userAddress = user.getAddress();
		if(userAddress == null){
			userAddress = new Address();
		}
		userAddress.setCity(addressToBeUpdated.getCity());
		userAddress.setState(addressToBeUpdated.getState());
		userAddress.setZipCode(addressToBeUpdated.getZipCode());
		userAddress.setUser(user);
		user.setAddress(userAddress);

		try{
			logger.info("User Saving address: " + user);
			userRepository.save(user);
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new CustomGenericException("Bad Data");
		}
	}
	
	public ResponseEntity<?> deleteAddress(Long id, Address addressToDelete) {
		try{
			userRepository.deleteByUserId(addressToDelete.getId());
			return ResponseEntity.ok(new MessageResponse("Success"));
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new CustomGenericException("Error Deletting User ");
		}
	}

	public ResponseEntity<?> addOrUpdateProfilePicture(UserDetailsImpl sessionUser, String base64Image){
		User user = getUser(sessionUser.getId());
		byte[] imageData = base64Image.getBytes();
		user.setProfilePicture(imageData);

		try{
			userRepository.save(user);
		}catch (Exception e){
			System.out.println("ErrorMessage: " + e.getMessage());
			throw new BadCredentialsException("Bad Data");
		}
		return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);

	}

	public ResponseEntity<?> getAllVolunteers(){
		try{
			List<User> allUsers = userRepository.findAllUsers();
			List<UserProfileAndAddress> userProfileAndAddresses = allUsers.stream()
					.filter(User::isVolunteer)
					.map(p -> {
							logger.info("Volunteer Details: " + p);
							return new UserProfileAndAddress(
							p.getFirstName(),
							p.getMiddleName(),
							p.getLastName(),
							p.getEmail(),
							p.getPhoneNumber(),
							p.isShowPhoneNumber(),
							p.getUniversity(),
							new String(p.getProfilePicture()),
							new UserAddress(p.getAddress().getCity(), p.getAddress().getState(), p.getAddress().getZipCode()
							));
					}

					)
					.sorted(Comparator.comparing(UserProfileAndAddress::getFirstName))
					.collect(Collectors.toList());
			if(userProfileAndAddresses.isEmpty()){
				return new ResponseEntity<>(new MessageResponse("No Volunteers Found"),HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(userProfileAndAddresses,HttpStatus.OK);
		}catch(Exception ex){
			throw new CustomGenericException(ex.getMessage());
		}
	}

	public ResponseEntity<?> getAllApplyForVolunteerRequest(){
		try {
			List<User> allUsers = userRepository.findAllUsers();
			List<UserProfileAndAddress> userProfileAndAddresses = allUsers.stream()
					.filter(User::isApplyForVolunteer)
					.map(p -> new UserProfileAndAddress(
							p.getFirstName(),
							p.getMiddleName(),
							p.getLastName(),
							p.getEmail(),
							p.getPhoneNumber(),
							p.isShowPhoneNumber(),
							p.getUniversity(),
							new String(p.getProfilePicture()),
							new UserAddress(p.getAddress().getCity(), p.getAddress().getState(), p.getAddress().getZipCode())
					))
					.collect(Collectors.toList());

			return new ResponseEntity<>(userProfileAndAddresses, HttpStatus.OK);
		} catch(Exception e){
			throw new CustomGenericException("Error approving volunteer request");
		}
	}

	public ResponseEntity<?> getAllStudents(){

		try{
			List<User> allUsers = userRepository.findAllUsers();
			List<UserProfileAndAddress> userProfileAndAddress = allUsers.stream()
					.filter(User::isStudent)
					.map(user -> {
						logger.info("Student Details: " + user);
						return new UserProfileAndAddress(
							user.getFirstName(),
							user.getMiddleName(),
							user.getLastName(),
							user.getEmail(),
							user.getPhoneNumber(),
							user.isShowPhoneNumber(),
							user.getUniversity(),
							new String(user.getProfilePicture()),
							new UserAddress(user.getAddress().getCity(), user.getAddress().getState(), user.getAddress().getZipCode())
					);
					})
					.sorted(Comparator.comparing(UserProfileAndAddress::getFirstName))
					.collect(Collectors.toList());
			return new ResponseEntity<>(userProfileAndAddress,HttpStatus.OK);
		} catch(Exception e){
			throw new CustomGenericException("Error gettting all students");
		}
	}

	public ResponseEntity<?> deleteProfilePicture(UserDetailsImpl userSession) {
		User user = getUser(userSession.getId());
		user.setProfilePicture(null);
		try{
			userRepository.save(user);
		}catch (Exception e){
			logger.error("Error while deleting picture: " + e.getMessage());
			throw new CustomGenericException(e.getMessage());
		}
		return new ResponseEntity<>(new MessageResponse("Success"), HttpStatus.OK);
	}
}
