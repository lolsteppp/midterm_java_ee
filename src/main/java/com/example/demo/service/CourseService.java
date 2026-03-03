package com.example.demo.service;

import com.example.demo.dto.CourseRequest;
import com.example.demo.dto.CourseResponse;
import com.example.demo.entity.Course;
import com.example.demo.entity.Student;
import com.example.demo.exception.CourseNotFoundException;
import com.example.demo.exception.DuplicateEmailException;
import com.example.demo.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;

    public List<CourseResponse> getCoursesByStudentId(Long studentId) {
        studentService.findStudentById(studentId);
        return courseRepository.findAllByStudentId(studentId)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CourseResponse getCourseById(Long courseId) {
        return toResponse(findCourseById(courseId));
    }

    @Transactional
    public CourseResponse createCourse(Long studentId, CourseRequest request) {
        Student student = studentService.findStudentById(studentId);

        if (courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new DuplicateEmailException("Course code '" + request.getCourseCode() + "' already exists");
        }

        Course course = Course.builder()
                .courseName(request.getCourseName())
                .courseCode(request.getCourseCode())
                .description(request.getDescription())
                .credits(request.getCredits())
                .student(student)
                .build();

        return toResponse(courseRepository.save(course));
    }

    @Transactional
    public CourseResponse updateCourse(Long courseId, CourseRequest request) {
        Course course = findCourseById(courseId);

        if (!course.getCourseCode().equals(request.getCourseCode())
                && courseRepository.existsByCourseCode(request.getCourseCode())) {
            throw new DuplicateEmailException("Course code '" + request.getCourseCode() + "' already exists");
        }

        course.setCourseName(request.getCourseName());
        course.setCourseCode(request.getCourseCode());
        course.setDescription(request.getDescription());
        course.setCredits(request.getCredits());

        return toResponse(courseRepository.save(course));
    }

    @Transactional
    public void deleteCourse(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new CourseNotFoundException(courseId);
        }
        courseRepository.deleteById(courseId);
    }

    private Course findCourseById(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));
    }

    private CourseResponse toResponse(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .courseName(course.getCourseName())
                .courseCode(course.getCourseCode())
                .description(course.getDescription())
                .credits(course.getCredits())
                .studentId(course.getStudent().getId())
                .build();
    }
}
