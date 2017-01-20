package com.epam.spring.core.service;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.spring.core.domain.Auditorium;


/**
 * @author alehstruneuski
 */
public interface IAuditoriumService {

    /**
     * Getting all auditoriums from the system
     * 
     * @return set of all auditoriums
     */
    public @Nonnull Collection<Auditorium> getAll();

    /**
     * Finding auditorium by name
     * 
     * @param name
     *            Name of the auditorium
     * @return found auditorium or <code>null</code>
     */
    public @Nullable Auditorium getByName(@Nonnull String name);
    
    public @Nullable Auditorium getById(@Nonnull Long id);

    public @Nullable Auditorium save(@Nonnull Auditorium auditorium);
    
    public @Nullable void remove(@Nonnull Auditorium auditorium);



}
