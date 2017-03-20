package com.epam.spring.core.web.rest.controller;

import com.epam.spring.core.domain.Ticket;
import com.epam.spring.core.service.ITicketService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Added actually for tests in task only as
 * there were no rest operations returning
 * one or list of tickets.
 */
@RestController("restTicketController")
@RequestMapping("/api/ticket")
public class TicketController {
    private static final Log LOGGER = LogFactory.getLog(TicketController.class);

    private final ITicketService ticketService;

    @Autowired
    public TicketController(ITicketService ticketService) {
        this.ticketService = ticketService;
        LOGGER.info("Initializing is completed");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Ticket> ticket(@PathVariable Long id) {
        LOGGER.info("ticket GET request");
        LOGGER.info("id: " + id);

        Ticket ticket = ticketService.getById(id);
        if (ticket == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(ticket, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Collection<Ticket>> all() {
        LOGGER.info("all GET request");

        Collection<Ticket> tickets = ticketService.getAll();
        if (tickets.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(tickets, HttpStatus.OK);
    }
}
