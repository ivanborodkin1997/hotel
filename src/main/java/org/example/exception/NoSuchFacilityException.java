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
public class NoSuchFacilityException extends NoSuchElementException {

    private static final long serialVersionUID = 5510566190315081L;
    private String errorMessage;
    private int errorCode;

    public NoSuchFacilityException(int id) {
        errorMessage = "Facility with ID = " + id +" does not exist";
    }

}
