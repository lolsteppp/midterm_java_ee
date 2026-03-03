package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Course name must not be blank")
    @Size(min = 2, max = 100, message = "Course name must be between 2 and 100 characters")
    @Column(name = "course_name", nullable = false)
    private String courseName;

    @NotBlank(message = "Course code must not be blank")
    @Pattern(regexp = "^[A-Z]{2,5}\\d{3,4}$", message = "Course code must match pattern like 'CS101' or 'MATH2023'")
    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @Size(max = 300, message = "Description must not exceed 300 characters")
    private String description;

    @Min(value = 1, message = "Credits must be at least 1")
    @Max(value = 6, message = "Credits must not exceed 6")
    @Column(nullable = false)
    private Integer credits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
