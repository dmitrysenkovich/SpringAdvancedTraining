package com.epam.spring.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.epam.spring.core.domain.User;

/**
 * @author alehstruneuski
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{	

	public User findByEmail(String email);

}
