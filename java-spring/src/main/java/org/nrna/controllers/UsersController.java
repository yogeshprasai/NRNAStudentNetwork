package org.nrna.controllers;

import org.nrna.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    UserService userService;

    @GetMapping("/getAllVolunteers")
    public ResponseEntity<?> getAllVolunteers() {
        return userService.getAllVolunteers();
    }

    @GetMapping("/getAllStudents")
    public ResponseEntity<?> getAllStudents() {
        return userService.getAllStudents();
    }

    @GetMapping("/getAllApplyForVolunteerRequest")
    public ResponseEntity<?> getAllApplyForVolunteerRequest(){
        //only admin has this functionality available
        return userService.getAllApplyForVolunteerRequest();
    }

}
