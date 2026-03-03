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
public class CourseRequest {

    @NotBlank(message = "Course name must not be blank")
    private String courseName;

    @NotBlank(message = "Course code must not be blank")
    private String courseCode;

    private String description;

    @NotNull(message = "Credits must not be null")
    private Integer credits;
}
