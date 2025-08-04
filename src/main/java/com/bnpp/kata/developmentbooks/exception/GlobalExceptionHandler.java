package com.bnpp.kata.developmentbooks.exception;

import com.bnpp.kata.developmentbooks.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidBookException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBookException(InvalidBookException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder ()
                .message (HttpStatus.BAD_REQUEST.getReasonPhrase ())
                .status (HttpStatus.BAD_REQUEST.value ())
                .detailedErrorMessage (ex.getMessage ())
                .build ();

        return new ResponseEntity<> (errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {

        ErrorResponse errorResponse	= ErrorResponse.builder()
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .detailedErrorMessage(ex.getMessage())
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
