package com.sesc.mystudentportal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sesc.mystudentportal.model.UserDtls;

public interface UserRepository extends JpaRepository<UserDtls, Integer> {

    public boolean existsByCnumber(String cnumber);

    public UserDtls findByCnumber(String cnumber);
}
