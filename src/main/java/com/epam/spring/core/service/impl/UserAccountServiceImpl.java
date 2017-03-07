package com.epam.spring.core.service.impl;

import com.epam.spring.core.dao.UserAccountRepository;
import com.epam.spring.core.dao.UserRepository;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.domain.UserAccount;
import com.epam.spring.core.service.IUserAccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class UserAccountServiceImpl implements IUserAccountService {
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    @Autowired
    public UserAccountServiceImpl(UserRepository userRepository, UserAccountRepository userAccountRepository) {
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserAccount save(UserAccount userAccount) {
        return userAccountRepository.save(userAccount);
    }

    @Override
    public void remove(UserAccount userAccount) {
        userAccountRepository.delete(userAccount);
    }

    @Override
    public UserAccount getById(Long id) {
        return userAccountRepository.findOne(id);
    }

    @Override
    public Collection<UserAccount> getAll() {
        return userAccountRepository.findAll();
    }

    @Override
    @Transactional
    public void refill(Long userId, Double amount) {
        User user = userRepository.findOne(userId);
        UserAccount userAccount = user.getUserAccount();
        Double currentMoney = userAccount.getMoney();
        userAccount.setMoney(currentMoney + amount);
    }

    @Override
    @Transactional
    public UserAccount getByUserId(Long userId) {
        User user = userRepository.findOne(userId);

        return user.getUserAccount();
    }
}
