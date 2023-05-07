package com.sesc.mystudentportal.controller;

import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class HomeControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private HomeController homeController;

    @Mock
    private HttpSession session;

    @Mock
    private UserDtls userDtls;

    @Mock
    private Model model;

    @Test
    void createuser_withNewUser_shouldRedirectToRegisterAndSetSuccessMessage() {
        // Mock the required objects and method calls
        UserDtls user = new UserDtls();
        when(userService.checkCnumber(user.getCnumber())).thenReturn(false);
        when(userService.createUser(user)).thenReturn(userDtls);

        // Call the method under test
        String result = homeController.createuser(user, session);

        // Verify the expected interactions and assertions
        verify(session).setAttribute("msg", "Register Successfully");
        verify(userService).createUser(user);
        verify(userService, never()).checkCnumber(user.getCnumber());

        // Assert the result
        assertEquals("redirect:/register", result);
    }

    @Test
    void createuser_withExistingUser_shouldRedirectToRegisterAndSetErrorMessage() {
        // Mock the required objects and method calls
        UserDtls user = new UserDtls();
        when(userService.checkCnumber(user.getCnumber())).thenReturn(true);

        // Call the method under test
        String result = homeController.createuser(user, session);

        // Verify the expected interactions and assertions
        verify(session).setAttribute("msg", "Cnumber already exists");
        verify(userService, never()).createUser(user);
        verify(userService).checkCnumber(user.getCnumber());

        // Assert the result
        assertEquals("redirect:/register", result);
    }

    @Test
    void index_shouldReturnIndexView() {
        // Call the method under test
        String result = homeController.index();

        // Assert the result
        assertEquals("index", result);
    }

    @Test
    void login_shouldReturnLoginView() {
        // Call the method under test
        String result = homeController.login();

        // Assert the result
        assertEquals("login", result);
    }

    @Test
    void register_shouldReturnRegisterView() {
        // Call the method under test
        String result = homeController.register();

        // Assert the result
        assertEquals("register", result);
    }
}
