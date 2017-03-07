package com.epam.spring.core.dao;

import com.epam.spring.core.domain.User;
import com.epam.spring.core.domain.UserAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUser(User user);
}
