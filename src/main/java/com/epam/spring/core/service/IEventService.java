package com.epam.spring.core.service;

import com.epam.spring.core.domain.Event;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author alehstruneuski
 */
public interface IEventService extends IAbstractDomainObjectService<Event> {

    /**
     * Finding event by name
     * 
     * @param name
     *            Name of the event
     * @return found event or <code>null</code>
     */
    public @Nullable Event getByName(@Nonnull String name);

    void save(@Nonnull List<Event> objects);

    boolean exists(@Nonnull Event event);

    void remove(Long id);

    /*
     * Finding all events that air on specified date range
     * 
     * @param from Start date
     * 
     * @param to End date inclusive
     * 
     * @return Set of events
     */
    // public @Nonnull Set<Event> getForDateRange(@Nonnull LocalDate from,
    // @Nonnull LocalDate to);

    /*
     * Return events from 'now' till the the specified date time
     * 
     * @param to End date time inclusive
     * s
     * @return Set of events
     */
    // public @Nonnull Set<Event> getNextEvents(@Nonnull LocalDateTime to);

}
