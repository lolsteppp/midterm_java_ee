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
    @Size(min = 2, max = 100, message = "Course name must be between 2 and 100 characters")
    private String courseName;

    @NotBlank(message = "Course code must not be blank")
    @Pattern(regexp = "^[A-Z]{2,5}\\d{3,4}$", message = "Course code must match pattern like 'CS101' or 'MATH2023'")
    private String courseCode;

    @Size(max = 300, message = "Description must not exceed 300 characters")
    private String description;

    @NotNull(message = "Credits must not be null")
    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 6, message = "Credits must not exceed 6")
    private Integer credits;
}
