package com.epam.spring.core.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.spring.core.dao.IUserDao;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IUserService;

/**
 * @author alehstruneuski
 */
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserDao userDao;

	@Override
	public User save(User object) {
		return userDao.save(object);
	}

	@Override
	public void remove(User object) {
		userDao.remove(object);
	}

	@Override
	public User getById(Long id) {
		return userDao.getById(id);
	}

	@Override
	public Collection<User> getAll() {
		return userDao.getAll();
	}

	@Override
	public User getUserByEmail(String email) {
		return userDao.getUserByEmail(email);
	}
	
}
