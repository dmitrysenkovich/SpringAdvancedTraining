package com.epam.spring.core.web.controller;


import com.epam.spring.core.domain.Event;
import com.epam.spring.core.service.IEventService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.Collection;

@Controller
@RequestMapping("/event")
public class EventController {
    private static final Log LOGGER = LogFactory.getLog(EventController.class);

    private final IEventService eventService;

    @Autowired
    public EventController(IEventService eventService) {
        this.eventService = eventService;
        LOGGER.info("Initializing is completed");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView event(@RequestParam(required = false) String name) {
        LOGGER.info("event GET request");
        LOGGER.info("name: " + name);

        Collection<Event> events;
        if (name != null) {
            Event event = eventService.getByName(name);
            events = Arrays.asList(event);
        }
        else
            events = eventService.getAll();

        ModelAndView modelAndView = new ModelAndView("events");
        modelAndView.addObject("events", events);

        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView byId(@PathVariable Long id) {
        LOGGER.info("byId GET request");
        LOGGER.info("id: " + id);

        Event event = eventService.getById(id);

        ModelAndView modelAndView = new ModelAndView("events");
        modelAndView.addObject("events", Arrays.asList(event));

        return modelAndView;
    }
}
