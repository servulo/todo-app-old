package com.todoapp.dto;

import jakarta.validation.constraints.NotBlank;

public class AuthDTO {
    
    @NotBlank(message = "Username é obrigatório")
    public String username;

    @NotBlank(message = "Password é obrigatório")
    public String password;
}
