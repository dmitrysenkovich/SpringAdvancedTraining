package com.epam.spring.core.dao.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.epam.spring.core.dao.IUserDao;
import com.epam.spring.core.domain.User;

public class UserDaoImpl implements IUserDao {

	private static Map<Long, User> users = new HashMap<Long, User>();
	
	@Override
	public User save(User object) {
		return users.put(object.getId(), object);
	}

	@Override
	public void remove(User object) {
		users.remove(object.getId());
	}

	@Override
	public User getById(Long id) {
		return users.get(id);
	}

	@Override
	public Collection<User> getAll() {
		return users.values();
	}

	@Override
	public User getUserByEmail(String email) {
		List<User> usersByEmail = users.values().stream().
				filter(user -> user.getEmail().equals(email)).
				collect(Collectors.toList());
		return usersByEmail.isEmpty() ? null : usersByEmail.get(0);
	}
	
}
