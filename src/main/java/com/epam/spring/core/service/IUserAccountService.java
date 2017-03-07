package com.epam.spring.core.service;

import com.epam.spring.core.domain.UserAccount;

public interface IUserAccountService extends IAbstractDomainObjectService<UserAccount> {
    void refill(Long userId, Double amount);

    UserAccount getByUserId(Long userId);
}
