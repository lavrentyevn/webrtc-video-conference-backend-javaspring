package com.nikolay.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(UsernameExistsException e) {
        ExceptionResponse response = new ExceptionResponse("Username is already used", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(EmailExistsException e) {
        ExceptionResponse response = new ExceptionResponse("Email is already used", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(UserNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse("User not found", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(RoomNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse("Room with this name not found", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(RoomExistsException e) {
        ExceptionResponse response = new ExceptionResponse("Room with this name already exists", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }


    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(WrongPasswordException e) {
        ExceptionResponse response = new ExceptionResponse("Wrong password", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(UserNotInvitedException e) {
        ExceptionResponse response = new ExceptionResponse("This user is not invited", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(EventNotFoundException e) {
        ExceptionResponse response = new ExceptionResponse("Event not found", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(UserAlreadyVerifiedException e) {
        ExceptionResponse response = new ExceptionResponse("User is already verified", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(UserNotVerifiedException e) {
        ExceptionResponse response = new ExceptionResponse("User is not verified", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(InvalidTokenException e) {
        ExceptionResponse response = new ExceptionResponse("Token is invalid", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(ExpiredTokenException e) {
        ExceptionResponse response = new ExceptionResponse("Token has expired", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    private ResponseEntity<ExceptionResponse> handleException(UserAlreadyInRoomException e) {
        ExceptionResponse response = new ExceptionResponse("User is already in this room", LocalDateTime.now());

        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
