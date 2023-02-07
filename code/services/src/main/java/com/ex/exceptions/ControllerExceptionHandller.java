package com.ex.exceptions;


import java.util.Date;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.validation.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandller {
@ExceptionHandler(ResourceNotFoundException.class)
public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException ex,WebRequest request){
	ErrorMessage message =new ErrorMessage(HttpStatus.NOT_FOUND.value(),
			new Date(),
			ex.getMessage(),
			request.getDescription(false));
	return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
}
public ResponseEntity<ErrorMessage> UnauthorizedException(UnauthorizedException ex,WebRequest request){
	ErrorMessage message =new ErrorMessage(HttpStatus.UNAUTHORIZED.value(),
			new Date(),
			ex.getMessage(),
			request.getDescription(false));
	return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
}
public ResponseEntity<ErrorMessage> BadRequestException(BadRequestException ex,WebRequest request){
	ErrorMessage message =new ErrorMessage(HttpStatus.BAD_REQUEST.value(),
			new Date(),
			ex.getMessage(),
			request.getDescription(false));
	return new ResponseEntity<ErrorMessage>(message, HttpStatus.BAD_REQUEST);
}
public ResponseEntity<ErrorMessage> UsedException(UsedException ex,WebRequest request){
	ErrorMessage message =new ErrorMessage(HttpStatus.IM_USED.value(),
			new Date(),
			ex.getMessage(),
			request.getDescription(false));
	return new ResponseEntity<ErrorMessage>(message, HttpStatus.IM_USED);
}
@ExceptionHandler(MethodArgumentNotValidException.class)
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
    Map<String, String> fieldException = new HashMap<>();

    methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(e -> {
        fieldException.put(((FieldError) e).getField(), e.getDefaultMessage());
    });

    return fieldException;
}
@ExceptionHandler(ConstraintViolationException.class)
@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public Map<Path, String> handleConstraintViolationException(ConstraintViolationException ex) {
    Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
         
    Map<Path, String> fieldException = new HashMap<>();
    constraintViolations.stream().forEach(e -> {
        fieldException.put(e.getPropertyPath(), e.getMessage());
    });
    return fieldException;
}
}
