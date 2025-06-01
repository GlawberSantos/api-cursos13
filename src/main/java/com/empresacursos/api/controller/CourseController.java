package com.empresacursos.api.controller;

import com.empresacursos.api.dto.CourseCreateDTO;
import com.empresacursos.api.dto.CourseUpdateDTO;
import com.empresacursos.api.exception.ResourceNotFoundException;
import com.empresacursos.api.model.Course;
import com.empresacursos.api.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    // Método utilitário para converter String em enum Category
    private Course.Category parseCategory(String category) {
        try {
            return Course.Category.valueOf(category.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ResourceNotFoundException("Invalid category: " + category);
        }
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@Valid @RequestBody CourseCreateDTO courseDTO) {
        Course course = new Course();
        course.setName(courseDTO.getName());
        course.setCategory(parseCategory(courseDTO.getCategory())); // aqui converte pra enum
        course.setActive(true);
        Course savedCourse = courseService.createCourse(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
    }

    @GetMapping
    public Page<Course> getAllCourses(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return courseService.getFilteredCourses(name, category, PageRequest.of(page, size));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @Valid @RequestBody CourseUpdateDTO courseDTO) {
        Course existingCourse = courseService.getCourseById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));

        if (courseDTO.getName() != null) {
            existingCourse.setName(courseDTO.getName());
        }
        if (courseDTO.getCategory() != null) {
            existingCourse.setCategory(parseCategory(courseDTO.getCategory())); // usar utilitário
        }

        Course updatedCourse = courseService.updateCourse(existingCourse);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        if (!courseService.getCourseById(id).isPresent()) {
            throw new ResourceNotFoundException("Course not found with id: " + id);
        }
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/active")
    public ResponseEntity<Course> toggleActive(@PathVariable Long id) {
        Course course = courseService.getCourseById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
        course.setActive(!course.isActive());
        Course updatedCourse = courseService.updateCourse(course);
        return ResponseEntity.ok(updatedCourse);
    }
}
