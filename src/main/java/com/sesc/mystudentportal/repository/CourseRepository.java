package com.sesc.mystudentportal.repository;

import com.sesc.mystudentportal.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository <Course, Long>{

}
