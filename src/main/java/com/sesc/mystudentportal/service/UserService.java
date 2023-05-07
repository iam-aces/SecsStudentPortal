package com.sesc.mystudentportal.service;



import com.sesc.mystudentportal.model.UserDtls;

import java.util.List;

public interface UserService {

    UserDtls createUser(UserDtls user);

    boolean checkCnumber(String cnumber);

    UserDtls getUser (String cnumber);

    UserDtls updateUser(String userId, List<Long> courseIds);

}
