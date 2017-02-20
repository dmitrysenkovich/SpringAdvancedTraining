package com.epam.spring.core.web.controller;


import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IUserService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collection;

@Controller
@RequestMapping("/user")
public class UserController {
    private static final Log LOGGER = LogFactory.getLog(UserController.class);

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
        LOGGER.info("Initializing is completed");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView user(@RequestParam(required = false) String email) {
        LOGGER.info("user GET request");
        LOGGER.info("name: " + email);

        Collection<User> users;
        if (email != null) {
            User user = userService.getUserByEmail(email);
            users = Arrays.asList(user);
        }
        else
            users = userService.getAll();

        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", users);

        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView byId(@PathVariable Long id) {
        LOGGER.info("byId GET request");
        LOGGER.info("id: " + id);

        User user = userService.getById(id);

        ModelAndView modelAndView = new ModelAndView("users");
        modelAndView.addObject("users", Arrays.asList(user));

        return modelAndView;
    }
}
