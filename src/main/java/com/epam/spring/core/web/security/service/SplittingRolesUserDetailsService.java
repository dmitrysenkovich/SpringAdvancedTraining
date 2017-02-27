package com.epam.spring.core.web.security.service;

import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IUserService;
import com.epam.spring.core.web.security.model.SplittingRolesUserDetails;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;

@Component
public class SplittingRolesUserDetailsService implements UserDetailsService {
    private final IUserService userService;

    @Autowired
    public SplittingRolesUserDetailsService(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getUserByUserName(userName);
        SplittingRolesUserDetails splittingRolesUserDetails = new SplittingRolesUserDetails();
        try {
            BeanUtils.copyProperties(splittingRolesUserDetails, user);
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
            return null;
        }

        return splittingRolesUserDetails;
    }
}
