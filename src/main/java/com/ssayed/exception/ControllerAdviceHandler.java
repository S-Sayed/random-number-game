package com.ssayed.exception;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.ResourceAccessException;

import com.ssayed.model.ErrorMessage;

@ControllerAdvice
public class ControllerAdviceHandler {
	private final static Logger LOGGER = LoggerFactory.getLogger(ControllerAdviceHandler.class);

	@ExceptionHandler(value = { ResourceAccessException.class })
	public ResponseEntity<ErrorMessage> handleResourceAccessException(ResourceAccessException ex) {
		LOGGER.error("handleResourceAccessException - exception: ", ex);
		ErrorMessage errorMeg = new ErrorMessage(HttpStatus.NOT_FOUND.value(), new Date().toString(), ex.getMessage());
		return new ResponseEntity<ErrorMessage>(errorMeg, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { Exception.class, RuntimeException.class })
	public ResponseEntity<ErrorMessage> handleGeneralException(Exception ex) {
		LOGGER.error("handleGeneralException - exception: ", ex);
		ErrorMessage errorMeg = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date().toString(),
				"Something went wrong");

		return new ResponseEntity<ErrorMessage>(errorMeg, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}