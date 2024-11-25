package org.nrna.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MiscService {

    public ResponseEntity<?> getLatestNews(){

        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
