package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.UserAccountRepository;
import com.epam.spring.core.dao.UserRepository;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.domain.UserAccount;
import com.epam.spring.core.service.IUserService;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author alehstruneuski
 */
@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAccountRepository userAccountRepository;

	@Override
	public User save(User user) {
		if (user.getId() == null) {
			UserAccount userAccount = new UserAccount();
			userAccount.setMoney(0.0);
			user.setUserAccount(userAccount);
		}

		return userRepository.save(user);
	}

	@Override
	public void save(List<User> objects) {
		objects.forEach(this::save);
	}

	@Override
	public void remove(User object) {
		userRepository.delete(object.getId());
	}

	@Override
	public User getById(Long id) {
		User user = userRepository.findOne(id);
		if (user != null) {
			user.setUserAccount(null);
		}

		return user;
	}

	@Override
	public Collection<User> getAll() {
		List<User> targetCollection = new ArrayList<>();
		Iterable<User> eventIterator = userRepository.findAll();
		CollectionUtils.addAll(targetCollection, eventIterator.iterator());
		return targetCollection;
	}

	@Override
	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User getUserByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public boolean exists(User object) {
		return userRepository.findByUserNameOrEmail(object.getUserName(), object.getEmail()) != null;
	}
	
}
