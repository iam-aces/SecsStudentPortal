package com.sesc.mystudentportal.controller;


import com.sesc.mystudentportal.model.Course;
import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.service.AvlbCourseService;
import com.sesc.mystudentportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courses")
public class AvlbCourseController {


    @Autowired
    private AvlbCourseService avlbCourseService;
    @Autowired
    private UserService userService;


    public AvlbCourseController(AvlbCourseService avlbCourseService, UserService userService) {
        this.avlbCourseService = avlbCourseService;
        this.userService = userService;
    }

    @ModelAttribute
    public void init(Model model, Principal principal) {
        List<Course> courses = avlbCourseService.getCourse();
        List<Course> userCourses = userService.getUser(principal.getName()).getCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("userCourses", userCourses);

    }


    @ModelAttribute
    private void userDetails(Model m, Principal p) {
        m.addAttribute("user", getCurrentUser(p));
    }

    private UserDtls getCurrentUser(Principal principal){
        return userService.getUser(principal.getName());
    }

    @GetMapping("/list")
    public String courses() {
        return "user/courses";
    }

    @GetMapping("/enrol")
    public String enrol() {
        return "user/enrol";
    }

    @GetMapping("/userCourses")
    public String userCourses() {
        return "user/viewcourses";
    }

    @PostMapping("/enrol/create")
    public String enrolUser(WebRequest webRequest, Principal principal, HttpSession session){


        String[] idValues = webRequest.getParameterValues("ids");
        List<Long> idList = Arrays.stream(idValues)
                .map(Long::valueOf)
                .collect(Collectors.toList());

        System.out.println(idList);

        UserDtls updatedUser = userService.updateUser(principal.getName(),idList);
        System.out.println("User Enrolled Successfully");
        if (updatedUser != null) {
            session.setAttribute("msg", "User Enrolled Successfully");
        } else {
            session.setAttribute("msg", "Something wrong on server");
        }

        return "redirect:/courses/enrol";



    }


}
