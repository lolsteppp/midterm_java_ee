package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfileRequest {

    @NotBlank(message = "Bio must not be blank")
    private String bio;

    private String phoneNumber;

    @NotBlank(message = "Address must not be blank")
    private String address;

    private Double gpa;
}
