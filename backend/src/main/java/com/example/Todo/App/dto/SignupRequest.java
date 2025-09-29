package com.example.Todo.App.dto;

import com.example.Todo.App.enums.UserRole;
import lombok.Data;

@Data

public class SignupRequest {
    private String name;

    private String email;

    private String password;
    private UserRole userRole;

}
