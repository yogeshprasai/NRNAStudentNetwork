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
@RequestMapping("/api/helper")
public class HelperController {

    @Autowired
    UserService userService;

    @GetMapping("/getAllHelpers")
    public ResponseEntity<?> getAllHelpers() {
        return userService.getAllHelpers();
    }

}
