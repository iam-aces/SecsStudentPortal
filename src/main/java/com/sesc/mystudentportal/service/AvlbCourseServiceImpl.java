package com.sesc.mystudentportal.service;

import com.sesc.mystudentportal.model.Course;
import com.sesc.mystudentportal.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AvlbCourseServiceImpl implements AvlbCourseService{

    private final CourseRepository courseRepo;
    @Autowired
    public AvlbCourseServiceImpl(CourseRepository courseRepo) {
        this.courseRepo = courseRepo;
    }


    public List<Course> getCourse(){
        return courseRepo.findAll();
    }

    public List<Course> getAllCoursesById(List<Long> ids){
        return courseRepo.findAllById(ids);
    }
}
