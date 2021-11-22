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
public class EmptyListException extends RuntimeException{

    private static final long serialVersionUID = -3583517320063046294L;
    private String errorMessage;
    private int errorCode;

}
