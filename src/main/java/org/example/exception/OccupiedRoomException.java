package org.example.exception;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@RequiredArgsConstructor
public class OccupiedRoomException extends RuntimeException {

    private static final long serialVersionUID = 906577896378776721L;
    private String errorMessage;
    private int errorCode;

    public OccupiedRoomException(int id) {
        errorMessage = "Room with ID = " + id +" is occupied";
    }

}
