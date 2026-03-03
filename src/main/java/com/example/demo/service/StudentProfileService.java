package com.example.demo.service;

import com.example.demo.dto.StudentProfileRequest;
import com.example.demo.dto.StudentProfileResponse;
import com.example.demo.entity.Student;
import com.example.demo.entity.StudentProfile;
import com.example.demo.exception.ProfileAlreadyExistsException;
import com.example.demo.exception.StudentNotFoundException;
import com.example.demo.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentProfileService {

    private final StudentProfileRepository profileRepository;
    private final StudentService studentService;

    public StudentProfileResponse getProfileByStudentId(Long studentId) {
        StudentProfile profile = profileRepository.findByStudentId(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student profile not found for student id: " + studentId));
        return toResponse(profile);
    }

    @Transactional
    public StudentProfileResponse createProfile(Long studentId, StudentProfileRequest request) {
        Student student = studentService.findStudentById(studentId);

        if (profileRepository.existsByStudentId(studentId)) {
            throw new ProfileAlreadyExistsException(studentId);
        }

        StudentProfile profile = StudentProfile.builder()
                .bio(request.getBio())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .gpa(request.getGpa())
                .student(student)
                .build();

        return toResponse(profileRepository.save(profile));
    }

    @Transactional
    public StudentProfileResponse updateProfile(Long studentId, StudentProfileRequest request) {
        StudentProfile profile = profileRepository.findByStudentId(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Profile not found for student id: " + studentId));

        profile.setBio(request.getBio());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setAddress(request.getAddress());
        profile.setGpa(request.getGpa());

        return toResponse(profileRepository.save(profile));
    }

    @Transactional
    public void deleteProfile(Long studentId) {
        StudentProfile profile = profileRepository.findByStudentId(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Profile not found for student id: " + studentId));
        profileRepository.delete(profile);
    }

    private StudentProfileResponse toResponse(StudentProfile profile) {
        return StudentProfileResponse.builder()
                .id(profile.getId())
                .bio(profile.getBio())
                .phoneNumber(profile.getPhoneNumber())
                .address(profile.getAddress())
                .gpa(profile.getGpa())
                .build();
    }
}
