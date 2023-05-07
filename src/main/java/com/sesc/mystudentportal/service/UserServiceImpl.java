package com.sesc.mystudentportal.service;


import com.sesc.mystudentportal.model.Account;
import com.sesc.mystudentportal.model.Course;
import com.sesc.mystudentportal.model.Invoice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.repository.UserRepository;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncode;

    @Autowired
    private AvlbCourseService avlbCourseService;
    @Autowired
    private IntegrationService integrationService;

    @Override
    public UserDtls createUser(UserDtls user) {

        user.setPassword(passwordEncode.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        UserDtls userDtls = userRepo.save(user);
        Account account = new Account();
        account.setStudentId(userDtls.getCnumber());
        integrationService.notifyStudentCreated(account);
        return userDtls;


    }

    @Override
    public boolean checkCnumber(String cnumber) {

        return userRepo.existsByCnumber(cnumber);
    }

    @Override
    public UserDtls getUser(String cnumber) {
        return userRepo.findByCnumber(cnumber);
    }


    public UserDtls updateUser(String userId, List<Long> courseIds) {
        UserDtls userDtls = userRepo.findByCnumber(userId);

        if (userDtls==null) {
            throw new IllegalArgumentException("User not found");
        }
        System.out.println("Get the ID's in Long: "+ courseIds);
        // Fetch the list of courses based on the provided course IDs
        List<Course> courses = avlbCourseService.getAllCoursesById(courseIds);
        userDtls.setCourses(courses);

        UserDtls updatedUser = userRepo.save(userDtls);

        Invoice invoice = notifySubscribers(updatedUser);
        //Pulling reference as a list. because courses are not added in single queries. courses can be added in list. this creates a list of generated referecnes.
        System.out.println(invoice.getReference());
        return updatedUser;
    }


    private Invoice notifySubscribers(UserDtls updatedUser) {
        Account account = new Account();
        account.setStudentId(updatedUser.getCnumber());
        Invoice invoice = new Invoice();
        for (Course course: updatedUser.getCourses()) {
            invoice.setAccount(account);
            invoice.setType(Invoice.Type.TUITION_FEES);
            invoice.setAmount(course.getFee());
            invoice.setDueDate(LocalDate.now().plusMonths(1));
            invoice = integrationService.createCourseFeeInvoice(invoice);
        }
        return invoice;
    }

    public ModelAndView getGraduationStatus(UserDtls userDtls) {
        if (userDtls == null) {
            throw new IllegalArgumentException("User not found");
        }
        Account account = integrationService.getStudentPaymentStatus(userDtls);
        ModelAndView modelAndView = new ModelAndView("graduation");
        modelAndView.addObject("balanceOutstanding", account.isHasOutstandingBalance());
        modelAndView.addObject("message", account.isHasOutstandingBalance() ? "ineligible to graduate" : "eligible to graduate");
        return modelAndView;
    }

}
