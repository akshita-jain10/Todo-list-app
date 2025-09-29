package com.example.Todo.App.dto;

import com.example.Todo.App.enums.UserRole;
import lombok.Data;

@Data
public class AuthenticationResponse {
    private String jwt;
    private String userId;
    private UserRole userRole;
}
