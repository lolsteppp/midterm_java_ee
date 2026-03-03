package com.example.demo.exception;

public class ProfileAlreadyExistsException extends RuntimeException {
    public ProfileAlreadyExistsException(Long studentId) {
        super("Profile already exists for student with id: " + studentId);
    }
}
