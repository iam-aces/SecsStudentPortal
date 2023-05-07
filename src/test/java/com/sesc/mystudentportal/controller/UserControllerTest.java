package com.sesc.mystudentportal.controller;
import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Mock
    private Principal principal;

    @Mock
    private Model model;

    @Test
    void userDetails_shouldAddUserToModel() {
        // Mock the required objects and method calls
        String cnumber = "C1234";
        UserDtls user = new UserDtls();
        when(principal.getName()).thenReturn(cnumber);
        when(userRepository.findByCnumber(cnumber)).thenReturn(user);


        // Verify the expected interactions and assertions
        verify(model).addAttribute("user", user);
        verify(userRepository).findByCnumber(cnumber);
    }

    @Test
    void home_shouldReturnHomeView() {
        // Call the method under test
        String result = userController.home();

        // Assert the result
        assertEquals("user/home", result);
    }

    @Test
    void courses_shouldReturnCoursesView() {
        // Call the method under test
        String result = userController.courses();

        // Assert the result
        assertEquals("user/courses", result);
    }

    @Test
    void graduation_shouldReturnGraduationView() {
        // Call the method under test
        String result = userController.graduation();

        // Assert the result
        assertEquals("user/graduation", result);
    }

    @Test
    void profile_shouldReturnProfileView() {
        // Call the method under test
        String result = userController.profile();

        // Assert the result
        assertEquals("user/profile", result);
    }
}
