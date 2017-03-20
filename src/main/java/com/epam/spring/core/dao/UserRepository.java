package com.epam.spring.core.dao;

import com.epam.spring.core.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author alehstruneuski
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{	

	public User findByEmail(String email);

	public User findByUserName(String userName);

	public User findByUserNameOrEmail(String userName, String email);

}
