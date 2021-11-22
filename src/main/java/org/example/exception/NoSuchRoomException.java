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
public class NoSuchRoomException extends NoSuchElementException {

    private static final long serialVersionUID = 8176698016230756734L;
    private String errorMessage;
    private int errorCode;

    public NoSuchRoomException(int id) {
        errorMessage = "Room with ID = " + id +" does not exist";
    }

}
