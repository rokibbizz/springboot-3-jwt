package com.rokib.jwt.application.usecase;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface IUserUseCase {


    ResponseEntity<String> signUp();
}
