package com.sesc.mystudentportal.config;

import com.sesc.mystudentportal.model.Course;
import com.sesc.mystudentportal.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CourseConfig {

    @Bean
    CommandLineRunner commandLineRunner (CourseRepository repository){
        return args -> {
            Course sesc = new Course(
                    "SESC",120.0, "Software Engineering Service Computing");
            Course rsec = new Course(
                    "RSEC", 125.0 ,"Reverse Software Engineering Computing");

            repository.saveAll(
                    Arrays.asList(sesc, rsec)
            );
        };
    }

}
