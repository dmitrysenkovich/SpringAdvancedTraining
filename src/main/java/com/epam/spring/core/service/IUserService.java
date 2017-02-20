package com.epam.spring.core.service;

import com.epam.spring.core.domain.User;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author alehstruneuski
 */
public interface IUserService extends IAbstractDomainObjectService<User> {

    /**
     * Finding user by email
     * 
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    public @Nullable User getUserByEmail(@Nonnull String email);

    void save(@Nonnull List<User> objects);
}
