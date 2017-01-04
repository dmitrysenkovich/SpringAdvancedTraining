package com.epam.spring.core.service.discount;

import java.time.LocalDateTime;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.User;

/**
 * @author alehstruneuski
 */
public interface IDiscount {

	public double getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets);
}
