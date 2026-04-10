package com.todoapp.dto;

import jakarta.validation.constraints.NotBlank;

public class TaskDTO {

    @NotBlank(message = "O título é obrigatório")
    public String title;

    public String description;

    public boolean completed;

}