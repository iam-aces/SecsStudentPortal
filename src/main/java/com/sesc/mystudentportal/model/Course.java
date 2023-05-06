package com.sesc.mystudentportal.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double fee;
    private String title;
    @ManyToOne
    private Student student;





    public Course(String description, double fee, String title) {
        this.description = description;
        this.fee = Double.valueOf(fee);
        this.title = title;
    }

    public Course() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
