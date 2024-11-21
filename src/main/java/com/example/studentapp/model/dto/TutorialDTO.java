package com.example.studentapp.model.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TutorialDTO {

    @NotBlank(message = "Title is required and cannot be blank")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;

    @NotNull(message = "Description is required")
    @Size(max = 500, message = "Description must be less than 500 characters")
    private String description;

    private boolean published;
}