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
public class NoSuchGuestException extends NoSuchElementException {

    private static final long serialVersionUID = -4185313816593285799L;
    private String errorMessage;
    private int errorCode;

    public NoSuchGuestException(int id) {
        errorMessage = "Guest with ID = " + id +" does not exist";
    }

}
