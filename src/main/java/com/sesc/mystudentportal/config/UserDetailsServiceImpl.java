package com.sesc.mystudentportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sesc.mystudentportal.model.UserDtls;
import com.sesc.mystudentportal.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String cnumber) throws UsernameNotFoundException {

        UserDtls user = userRepo.findByCnumber(cnumber);

        if (user != null) {
            return new CustomUserDetails(user);
        }

        throw new UsernameNotFoundException("user not available");
    }

}