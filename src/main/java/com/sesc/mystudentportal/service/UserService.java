package com.sesc.mystudentportal.service;


import com.sesc.mystudentportal.model.UserDtls;

public interface UserService {

    public UserDtls createUser(UserDtls user);

    public boolean checkCnumber(String cnumber);

}
