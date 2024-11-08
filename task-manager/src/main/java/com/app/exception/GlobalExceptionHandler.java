package com.app.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<ExceptionDefinition> taskNotFoundException(TaskNotFoundException ex, WebRequest wb){
		return new ResponseEntity<ExceptionDefinition>(new ExceptionDefinition(
				LocalDateTime.now(),
				ex.getMessage(),
				wb.getDescription(false)
				),
				HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionDefinition> noHandlerExceptionHandler(NoHandlerFoundException ex, WebRequest wb) {
		return new ResponseEntity<ExceptionDefinition>(new ExceptionDefinition(
				LocalDateTime.now(),
				ex.getMessage(),
				wb.getDescription(false)
				),
				HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDefinition> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex, WebRequest wb) {
		
		List<ObjectError> allErrors     = ex.getBindingResult().getAllErrors();
        List<String>      errorMessages = MethodArgumentNotValidException.errorsToStringList(allErrors);
        System.out.println("From MethodArgumentNotValidException ");
		return new ResponseEntity<ExceptionDefinition>(new ExceptionDefinition(
				LocalDateTime.now(),
				String.join(", ", errorMessages),
				wb.getDescription(false)
				),
				HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionDefinition> globalExceptionHandler(Exception ex, WebRequest wb){
//		System.out.println("From Exception ");
		return new ResponseEntity<ExceptionDefinition>(new ExceptionDefinition(
				LocalDateTime.now(),
				ex.getMessage(),
				wb.getDescription(false)
				),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<ExceptionDefinition> duplicateExceptionHandler(DataIntegrityViolationException ex, WebRequest wb){

		org.hibernate.exception.ConstraintViolationException cause =
              (org.hibernate.exception.ConstraintViolationException) ex.getCause();
		
		String errMessage = cause.getSQLException().getMessage();
		
		System.out.println(errMessage);
		
		return new ResponseEntity<ExceptionDefinition>(new ExceptionDefinition(
				LocalDateTime.now(),
				errMessage,
				wb.getDescription(false)
				),
				HttpStatus.BAD_REQUEST);
	}
	
	
	
//	@ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
//        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException) {
//            org.hibernate.exception.ConstraintViolationException cause =
//                    (org.hibernate.exception.ConstraintViolationException) ex.getCause();
//            
//            if (cause.getErrorCode() == 1062) {
//                // MySQL duplicate entry error code
//                String errorMessage = "Duplicate entry found for email: " + cause.getSQLException().getMessage();
//                return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
//            }
//        }
//        
//        // Handle other DataIntegrityViolationException cases if needed
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing your request.");
//    }

	
}
