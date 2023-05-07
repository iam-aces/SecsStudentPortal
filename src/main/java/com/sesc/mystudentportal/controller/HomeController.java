package com.sesc.mystudentportal.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.service.UserService;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/signin")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/createUser")
    public String createuser(@ModelAttribute UserDtls user, HttpSession session) {

        // System.out.println(user); used to test the data from command line

        boolean f = userService.checkCnumber(user.getCnumber());

        if (f) {
            session.setAttribute("msg", "Cnumber already exists");
        }

        else {
            UserDtls userDtls = userService.createUser(user);
            if (userDtls != null) {
                session.setAttribute("msg", "Register Successfully");
            } else {
                session.setAttribute("msg", "Something wrong on server");
            }
        }

        return "redirect:/register";
    }

}