package com.sesc.mystudentportal.service;

import com.sesc.mystudentportal.model.Course;
import com.sesc.mystudentportal.repository.CourseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class AvlbCourseServiceImplTest {

    private AvlbCourseServiceImpl avlbCourseService;

    @Mock
    private CourseRepository courseRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        avlbCourseService = new AvlbCourseServiceImpl(courseRepository);
    }

    @Test
    public void testGetCourse() {
        // Mock the course repository's findAll() method to return a list of courses
        List<Course> expectedCourses = Arrays.asList(
                new Course(
                    "SESC",120.0, "Software Engineering Service Computing"));
        when(courseRepository.findAll()).thenReturn(expectedCourses);

        // Call the method under test
        List<Course> actualCourses = avlbCourseService.getCourse();

        // Verify the expected result
        assertEquals(expectedCourses, actualCourses);
    }

    @Test
    public void testGetAllCoursesById() {
        // Mock the course repository's findAllById() method to return a list of courses
        List<Long> ids = Arrays.asList(1L, 2L, 3L);
        List<Course> expectedCourses = Arrays.asList(
                new Course(
                        "SESC",120.0, "Software Engineering Service Computing"));
        when(courseRepository.findAllById(ids)).thenReturn(expectedCourses);

        // Call the method under test
        List<Course> actualCourses = avlbCourseService.getAllCoursesById(ids);

        // Verify the expected result
        assertEquals(expectedCourses, actualCourses);
    }
}
