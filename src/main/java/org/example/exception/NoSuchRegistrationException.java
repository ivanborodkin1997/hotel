package org.example.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class NoSuchRegistrationException extends NoSuchElementException {

    private static final long serialVersionUID = 5533722441022554389L;
    private String errorMessage;
    private int errorCode;

    public NoSuchRegistrationException(int id) {
        errorMessage = "Registration with ID = " + id +" does not exist";
    }

}
