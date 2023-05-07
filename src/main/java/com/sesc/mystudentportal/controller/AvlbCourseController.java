package com.sesc.mystudentportal.controller;

import com.sesc.mystudentportal.model.Course;
import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.repository.UserRepository;
import com.sesc.mystudentportal.service.AvlbCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/courses")
public class AvlbCourseController {


    @Autowired
    private AvlbCourseService avlbCourseService;
    @Autowired
    private UserRepository userRepo;


    public AvlbCourseController(AvlbCourseService avlbCourseService, UserRepository userRepo) {
        this.avlbCourseService = avlbCourseService;
        this.userRepo = userRepo;
    }

    @ModelAttribute
    public void init(Model model) {
        List<Course> courses = avlbCourseService.getCourse();
        model.addAttribute("courses", courses);

    }


    @ModelAttribute
    private void userDetails(Model m, Principal p) {
        m.addAttribute("user", getCurrentUser(p));
    }

    private UserDtls getCurrentUser(Principal principal){
        return userRepo.findByCnumber(principal.getName());
    }

    @GetMapping("/list")
    public String courses() {
        return "user/courses";
    }

    @GetMapping("/enrol")
    public String enrol() {
        return "user/enrol";
    }

    @PostMapping("/enrol/create")
    public String enrolUser(WebRequest webRequest, Principal principal){
        getCurrentUser(principal);

        System.out.println("I have the course ids: "+webRequest.getParameter("CourseIds"));
        return "/user/enrol";

        //avlbCourseService.enrolUser(UserDtls,)

    }


}
