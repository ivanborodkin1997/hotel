package org.example.advice;

import org.example.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EmptyInputException.class)
    public ResponseEntity<String> emptyInputException(EmptyInputException emptyInputException) {
        return new ResponseEntity<>("Input field is Empty, Please look into it", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchRoomException.class)
    public ResponseEntity<String> noSuchRoomException(NoSuchRoomException noSuchRoomException) {
        return new ResponseEntity<>(noSuchRoomException.getErrorMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchGuestException.class)
    public ResponseEntity<String> noSuchGuestException(NoSuchGuestException noSuchGuestException) {
        return new ResponseEntity<>(noSuchGuestException.getErrorMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchFacilityException.class)
    public ResponseEntity<String> noSuchFacilityException(NoSuchFacilityException noSuchFacilityException) {
        return new ResponseEntity<>(noSuchFacilityException.getErrorMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchRegistrationException.class)
    public ResponseEntity<String> noSuchRegistrationException(NoSuchRegistrationException noSuchRegistrationException) {
        return new ResponseEntity<>(noSuchRegistrationException.getErrorMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EmptyListException.class)
    public ResponseEntity<String> emptyListException(EmptyListException emptyListException) {
        return new ResponseEntity<>("The list is empty. Try another query", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OccupiedRoomException.class)
    public ResponseEntity<String> occupiedRoomException(OccupiedRoomException occupiedRoomException) {
        return new ResponseEntity<>(occupiedRoomException.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity<String> indexOutOfBoundsException(IndexOutOfBoundsException IndexOutOfBoundsException) {
        return new ResponseEntity<>("The room did not have so many visitors", HttpStatus.NOT_FOUND);
    }

}
