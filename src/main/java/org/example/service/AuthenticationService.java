package org.example.service;

import org.example.dto.AuthenticationRequestDto;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> checkAuthentication(AuthenticationRequestDto request);

}
