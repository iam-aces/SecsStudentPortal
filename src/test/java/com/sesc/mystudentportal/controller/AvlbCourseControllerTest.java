package com.sesc.mystudentportal.controller;

import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.service.AvlbCourseService;
import com.sesc.mystudentportal.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AvlbCourseControllerTest {

    @Mock
    private AvlbCourseService avlbCourseService;

    @Mock
    private UserService userService;

    @InjectMocks
    private AvlbCourseController avlbCourseController;

    @Mock
    private WebRequest webRequest;

    @Mock
    private Principal principal;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void enrolUser_shouldEnrolUserAndRedirect() {
        // Mock the required objects and method calls
        String[] idValues = { "1", "2", "3" };
        List<Long> idList = Arrays.asList(1L, 2L, 3L);
        when(webRequest.getParameterValues("ids")).thenReturn(idValues);
        when(principal.getName()).thenReturn("username");
        when(userService.updateUser("username", idList)).thenReturn(new UserDtls());
        HttpSession session = mock(HttpSession.class);

        // Call the method under test
        String result = avlbCourseController.enrolUser(webRequest, principal, session);

        // Verify the expected interactions and assertions
        verify(session).setAttribute("msg", "User Enrolled Successfully");
        verify(userService).updateUser("username", idList);
        verify(avlbCourseService, never()).getCourse(); // Since it's not used in this method

        // Assert the result
        assertEquals("redirect:/courses/enrol", result);
    }
}
