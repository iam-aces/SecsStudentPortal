package com.sesc.mystudentportal.service;
import com.sesc.mystudentportal.model.Course;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AvlbCourseService{
     List<Course> getCourse();
}
