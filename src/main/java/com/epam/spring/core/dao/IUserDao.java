package com.epam.spring.core.dao;

import java.util.Collection;

import com.epam.spring.core.domain.User;

public interface IUserDao {	

	public User save(User object);

	public void remove(User object);

	public User getById(Long id);

	public Collection<User> getAll();

	public User getUserByEmail(String email);

}
