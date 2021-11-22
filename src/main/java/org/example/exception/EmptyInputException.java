package org.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmptyInputException extends RuntimeException {

    private static final long serialVersionUID = -2746610616891737409L;
    private String errorMessage;
    private int errorCode;

}
