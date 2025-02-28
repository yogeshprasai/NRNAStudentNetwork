package org.nrna.controllers;

import org.nrna.services.MiscService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class MiscController {

    @Autowired
    MiscService miscService;

    @RequestMapping(value = "/news/latestNews", method = RequestMethod.GET)
    public ResponseEntity<?> latestNews() {
        return miscService.getLatestNews();
    }

    @GetMapping(value="/topUniversities")
    public ResponseEntity<?> getTopUniversities(){
        return new ResponseEntity<>(miscService.getTopUniversities(), HttpStatus.OK);
    }

    @GetMapping(value="/universityOutreachList")
    public ResponseEntity<?> getUniversityOutreachList(){
        return new ResponseEntity<>(miscService.getListOfUniversityOutreach(), HttpStatus.OK);
    }

}
