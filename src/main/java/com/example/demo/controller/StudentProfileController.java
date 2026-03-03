package com.example.demo.controller;

import com.example.demo.dto.StudentProfileRequest;
import com.example.demo.dto.StudentProfileResponse;
import com.example.demo.service.StudentProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students/{studentId}/profile")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService profileService;

    @GetMapping
    public ResponseEntity<StudentProfileResponse> getProfile(@PathVariable Long studentId) {
        return ResponseEntity.ok(profileService.getProfileByStudentId(studentId));
    }

    @PostMapping
    public ResponseEntity<StudentProfileResponse> createProfile(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentProfileRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(profileService.createProfile(studentId, request));
    }

    @PutMapping
    public ResponseEntity<StudentProfileResponse> updateProfile(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentProfileRequest request) {
        return ResponseEntity.ok(profileService.updateProfile(studentId, request));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(@PathVariable Long studentId) {
        profileService.deleteProfile(studentId);
        return ResponseEntity.noContent().build();
    }
}
