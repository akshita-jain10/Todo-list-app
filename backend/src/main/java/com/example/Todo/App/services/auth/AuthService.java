package com.example.Todo.App.services.auth;

import com.example.Todo.App.dto.SignupRequest;
import com.example.Todo.App.dto.UserDTO;

public interface AuthService {
   UserDTO signupUser (SignupRequest signupRequest);
   boolean hasUserWithEmail(String email);
}
