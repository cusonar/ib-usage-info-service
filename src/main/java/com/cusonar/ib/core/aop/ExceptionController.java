package com.cusonar.ib.core.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cusonar.ib.core.exception.NotFoundException;
import com.cusonar.ib.response.ExceptionResponse;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionController {
	
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ExceptionResponse> todoNotFoundExceptionHandler(Exception e) {
		return commonHandler(e, e.getMessage(), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> commonExceptionHandler(Exception e) {
		return commonHandler(e, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	private ResponseEntity<ExceptionResponse> commonHandler(Exception e, String message, HttpStatus status) {
		log.error(e.getMessage(), e);
		ExceptionResponse error = new ExceptionResponse();
		error.setErrorCode(status.value());
		error.setMessage(message);
		return new ResponseEntity<>(error, status);
	}
}
