package com.epam.spring.core.web.controller;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.service.IBookingService;
import com.epam.spring.core.service.IEventService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.Set;

@Controller
@RequestMapping("/ticket")
public class BookingController {
    private static final Log LOGGER = LogFactory.getLog(BookingController.class);

    private final IBookingService bookingService;
    private final IEventService eventService;

    @Autowired
    public BookingController(IBookingService bookingService, IEventService eventService) {
        this.bookingService = bookingService;
        this.eventService = eventService;
        LOGGER.info("Initializing is completed");
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public ModelAndView purchasedTicketsForEvent(@RequestParam Long eventId,
                                                 @RequestParam Date dateTime) {
        LOGGER.info("purchasedTicketsForEvent GET request");
        LOGGER.info("eventId: " + eventId);
        LOGGER.info("dateTime: " + dateTime);

        Event event = eventService.getById(eventId);
        Set<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(event, dateTime);

        ModelAndView modelAndView = new ModelAndView("ticketsForEvent");
        modelAndView.addObject("tickets", tickets);

        return modelAndView;
    }
}
