package com.sesc.mystudentportal.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    @ModelAttribute
    private void userDetails(Model m, Principal p) {
        String cnumber = p.getName();
        UserDtls user = userRepo.findByCnumber(cnumber);

        m.addAttribute("user", user);

    }

    @GetMapping("/")
    public String home() {
        return "user/home";
    }

//   @GetMapping("/enrol")
//    public String enrol() {
//        return "user/enrol";
//    }
    @GetMapping("/courses")
    public String courses() {
        return "user/courses";
    }
    @GetMapping("/graduation")
    public String graduation() {
        return "user/graduation";
    }@GetMapping("/profile")
    public String profile() {
        return "user/profile";
    }

}