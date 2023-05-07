package com.sesc.mystudentportal.service;

import com.sesc.mystudentportal.model.Course;
import com.sesc.mystudentportal.model.Invoice;
import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepo;

    @Mock
    private AvlbCourseService avlbCourseService;

    @Mock
    private IntegrationService integrationService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_validUser_shouldReturnSavedUser() {
        // Mock the required objects and method calls
        UserDtls user = new UserDtls();
        when(userRepo.save(user)).thenReturn(user);

        // Call the method under test
        UserDtls result = userService.createUser(user);

        // Verify the expected interactions and assertions
        verify(userRepo).save(user);
        assertNotNull(result);
        assertEquals(user, result);
    }

    @Test
    void checkCnumber_existingCnumber_shouldReturnTrue() {
        // Mock the required objects and method calls
        String cnumber = "123456789";
        when(userRepo.existsByCnumber(cnumber)).thenReturn(true);

        // Call the method under test
        boolean result = userService.checkCnumber(cnumber);

        // Verify the expected interactions and assertions
        verify(userRepo).existsByCnumber(cnumber);
        assertTrue(result);
    }

    @Test
    void checkCnumber_nonExistingCnumber_shouldReturnFalse() {
        // Mock the required objects and method calls
        String cnumber = "123456789";
        when(userRepo.existsByCnumber(cnumber)).thenReturn(false);

        // Call the method under test
        boolean result = userService.checkCnumber(cnumber);

        // Verify the expected interactions and assertions
        verify(userRepo).existsByCnumber(cnumber);
        assertFalse(result);
    }

    @Test
    void getUser_existingCnumber_shouldReturnUser() {
        // Mock the required objects and method calls
        String cnumber = "123456789";
        UserDtls user = new UserDtls();
        when(userRepo.findByCnumber(cnumber)).thenReturn(user);

        // Call the method under test
        UserDtls result = userService.getUser(cnumber);

        // Verify the expected interactions and assertions
        verify(userRepo).findByCnumber(cnumber);
        assertEquals(user, result);
    }

    @Test
    void getUser_nonExistingCnumber_shouldReturnNull() {
        // Mock the required objects and method calls
        String cnumber = "123456789";
        when(userRepo.findByCnumber(cnumber)).thenReturn(null);

        // Call the method under test
        UserDtls result = userService.getUser(cnumber);

        // Verify the expected interactions and assertions
        verify(userRepo).findByCnumber(cnumber);
        assertNull(result);
    }

    @Test
    void updateUser_existingUser_shouldReturnUpdatedUser() {
        // Mock the required objects and method calls
        String userId = "123456789";
        UserDtls userDtls = new UserDtls();
        List<Long> courseIds = new ArrayList<>();
        Course course = new Course();
        course.setId(1L);
        courseIds.add(1L);
        when(userRepo.findByCnumber(userId)).thenReturn(userDtls);
        when(avlbCourseService.getAllCoursesById(courseIds)).thenReturn(Arrays.asList(course));
        when(userRepo.save(userDtls)).thenReturn(userDtls);
        when(integrationService.createCourseFeeInvoice(any())).thenReturn(new Invoice());

        // Call the method under test
        UserDtls result = userService.updateUser(userId, courseIds);

        // Verify the expected interactions and assertions
        verify(userRepo).findByCnumber(userId);
        verify(avlbCourseService).getAllCoursesById(courseIds);
        verify(userRepo).save(userDtls);
        verify(integrationService).createCourseFeeInvoice(any());
        assertNotNull(result);
        assertEquals(userDtls, result);
    }
}

