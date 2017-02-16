package com.epam.spring.core.web.controllers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {

	private static final String USERS_VIEW = "user/users_view";
	private static final String USER_ACTION_VIEW = "user/user_action_view";

	@Autowired
	private IUserService userService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getUserByEmail(@RequestParam() String email) {
		User user = userService.getUserByEmail(email);
		
		ModelAndView usersView = new ModelAndView(USERS_VIEW);
		usersView.addObject("users", Arrays.asList(user));
		return usersView;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getUserById(@RequestParam("id") long id) {
		User user = userService.getById(id);
		
		ModelAndView usersView = new ModelAndView(USERS_VIEW);
		usersView.addObject("users", Arrays.asList(user));
		return usersView;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAllUsers() {
		Collection<User> allUsers = userService.getAll();
		
		ModelAndView allUsersView = new ModelAndView(USERS_VIEW);
		allUsersView.addObject("users", allUsers);
		return allUsersView;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ModelAndView removeAuditorium(@RequestParam() long id) {
		User userToDelete = new User();
		userToDelete.setId(id);
		userService.remove(userToDelete);
		
		ModelAndView actionView = new ModelAndView(USER_ACTION_VIEW);
		actionView.addObject("action", "removing");
		return actionView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveUser(@RequestBody() User auditoriumToSave) {
		userService.save(auditoriumToSave);
		
		ModelAndView actionView = new ModelAndView(USER_ACTION_VIEW);
		actionView.addObject("action", "persisting");
		return actionView;
	}
	
}
