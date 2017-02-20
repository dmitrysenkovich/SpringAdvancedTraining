package com.epam.spring.core.web.rest.controller;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IBookingService;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.service.IUserService;
import com.epam.spring.core.web.model.TicketInfo;
import com.epam.spring.core.web.pdf.PdfCreator;
import com.sun.deploy.util.StringUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

@RestController("restBookingController")
@RequestMapping("/api/ticket")
public class BookingController {
    private static final Log LOGGER = LogFactory.getLog(BookingController.class);

    private final IBookingService bookingService;
    private final IEventService eventService;
    private final IUserService userService;

    private final PdfCreator pdfCreator;

    @Autowired
    public BookingController(IBookingService bookingService, IEventService eventService,
                             IUserService userService, PdfCreator pdfCreator) {
        this.bookingService = bookingService;
        this.eventService = eventService;
        this.userService = userService;
        this.pdfCreator = pdfCreator;
        LOGGER.info("Initializing is completed");
    }

    @RequestMapping(value = "/price", method = RequestMethod.GET)
    public double ticketsPrice(@RequestParam Long eventId, @RequestParam Date dateTime,
                               @RequestParam Long userId, @RequestParam Set<Long> seats) {
        LOGGER.info("price GET request");
        LOGGER.info("eventId: " + eventId);
        LOGGER.info("dateTime: " + dateTime);
        LOGGER.info("userId: " + userId);
        LOGGER.info("seats: " + StringUtils.join(seats, ","));

        Event event = eventService.getById(eventId);
        User user = userService.getById(userId);

        return bookingService.getTicketsPrice(event, dateTime, user, seats);
    }

    @RequestMapping(value = "/book", method = RequestMethod.POST)
    public void book(@RequestBody List<TicketInfo> ticketInfos) {
        LOGGER.info("book POST request");
        LOGGER.info("ticketInfos: " + StringUtils.join(ticketInfos, ","));

        Set<Ticket> tickets = new HashSet<>();
        for (TicketInfo ticketInfo : ticketInfos) {
            User user = userService.getById(ticketInfo.getUserId());
            Event event = eventService.getById(ticketInfo.getEventId());
            Ticket ticket = new Ticket(user, event, ticketInfo.getDate(), ticketInfo.getSeat());
            tickets.add(ticket);
        }

        bookingService.bookTickets(tickets);
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public Set<Ticket> purchasedTicketsForEvent(@RequestParam Long eventId,
                                                @RequestParam Date dateTime) {
        LOGGER.info("purchasedTicketsForEvent GET request");
        LOGGER.info("eventId: " + eventId);
        LOGGER.info("dateTime: " + dateTime);

        Event event = eventService.getById(eventId);

        return bookingService.getPurchasedTicketsForEvent(event, dateTime);
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public void purchasedTicketsForEventInPdf(@RequestParam Long eventId,
                                              @RequestParam Date dateTime,
                                              HttpServletResponse response) throws IOException {
        LOGGER.info("purchasedTicketsForEventInPdf GET request");
        LOGGER.info("eventId: " + eventId);
        LOGGER.info("dateTime: " + dateTime);

        Event event = eventService.getById(eventId);
        Set<Ticket> tickets = bookingService.getPurchasedTicketsForEvent(event, dateTime);

        byte[] pdfInBytes = pdfCreator.createPDF(tickets);
        response.setHeader("Content-disposition", "attachment; filename=" + "tickets.pdf");
        response.setContentLength(pdfInBytes.length);

        response.getOutputStream().write(pdfInBytes);
        response.getOutputStream().flush();
    }
}
