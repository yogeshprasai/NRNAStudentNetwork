package org.nrna.services.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.mail.SendFailedException;

@RestControllerAdvice
public class ControllerExceptionHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.NOT_FOUND.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    logger.debug("Resource Not Found: ", ex);
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
  }
  
  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ErrorMessage> badCredentialsException(BadCredentialsException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.BAD_REQUEST.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    logger.debug("Bad Credentials: ", ex);
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(SendFailedException.class)
  public ResponseEntity<?> SendFailedException(SendFailedException ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
            HttpStatus.BAD_REQUEST.value(),
            new Date(),
            ex.getMessage(),
            request.getDescription(false)
    );
    logger.info("SendFailedException Error: ", ex);
    return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CustomGenericException.class)
  public ResponseEntity<ErrorMessage> customGenericException(Exception ex, WebRequest request) {
    ErrorMessage message = new ErrorMessage(
        HttpStatus.INTERNAL_SERVER_ERROR.value(),
        new Date(),
        ex.getMessage(),
        request.getDescription(false));

    logger.debug("Generic Exception: ", ex);
    return new ResponseEntity<ErrorMessage>(message, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
