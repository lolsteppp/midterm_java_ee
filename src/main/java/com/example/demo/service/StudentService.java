package com.example.demo.service;

import com.example.demo.dto.*;
import com.example.demo.entity.Student;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;

    public List<StudentResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public StudentResponse getStudentById(Long id) {
        return toResponse(findStudentById(id));
    }

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Student with email '" + request.getEmail() + "' already exists");
        }

        Student student = Student.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .age(request.getAge())
                .build();

        return toResponse(studentRepository.save(student));
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest request) {
        Student student = findStudentById(id);

        if (!student.getEmail().equals(request.getEmail())
                && studentRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("Student with email '" + request.getEmail() + "' already exists");
        }

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setEmail(request.getEmail());
        student.setAge(request.getAge());

        return toResponse(studentRepository.save(student));
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    public Student findStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public StudentResponse toResponse(Student student) {
        StudentProfileResponse profileResponse = null;
        if (student.getStudentProfile() != null) {
            var p = student.getStudentProfile();
            profileResponse = StudentProfileResponse.builder()
                    .id(p.getId())
                    .bio(p.getBio())
                    .phoneNumber(p.getPhoneNumber())
                    .address(p.getAddress())
                    .gpa(p.getGpa())
                    .build();
        }

        List<CourseResponse> courseResponses = student.getCourses() == null ? List.of() :
                student.getCourses().stream().map(c -> CourseResponse.builder()
                        .id(c.getId())
                        .courseName(c.getCourseName())
                        .courseCode(c.getCourseCode())
                        .description(c.getDescription())
                        .credits(c.getCredits())
                        .studentId(student.getId())
                        .build()
                ).collect(Collectors.toList());

        return StudentResponse.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .email(student.getEmail())
                .age(student.getAge())
                .studentProfile(profileResponse)
                .courses(courseResponses)
                .build();
    }
}
