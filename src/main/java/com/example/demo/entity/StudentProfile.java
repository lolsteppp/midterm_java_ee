package com.example.demo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "student_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Bio must not be blank")
    @Column(length = 500)
    private String bio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @NotBlank(message = "Address must not be blank")
    private String address;

    @Column(name = "gpa")
    private Double gpa;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;
}
