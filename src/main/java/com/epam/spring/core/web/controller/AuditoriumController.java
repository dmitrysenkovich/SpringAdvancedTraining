package com.epam.spring.core.web.controller;


import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.service.IAuditoriumService;

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
@RequestMapping("/auditorium")
public class AuditoriumController {
    private static final Log LOGGER = LogFactory.getLog(AuditoriumController.class);

    private final IAuditoriumService auditoriumService;

    @Autowired
    public AuditoriumController(IAuditoriumService auditoriumService) {
        this.auditoriumService = auditoriumService;
        LOGGER.info("Initializing is completed");
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView auditorium(@RequestParam(required = false) String name) {
        LOGGER.info("auditorium GET request");
        LOGGER.info("name: " + name);

        Collection<Auditorium> auditoriums;
        if (name != null) {
            Auditorium auditorium = auditoriumService.getByName(name);
            auditoriums = Arrays.asList(auditorium);
        }
        else
            auditoriums = auditoriumService.getAll();

        ModelAndView modelAndView = new ModelAndView("auditoriums");
        modelAndView.addObject("auditoriums", auditoriums);

        return modelAndView;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ModelAndView byId(@PathVariable Long id) {
        LOGGER.info("byId GET request");
        LOGGER.info("id: " + id);

        Auditorium auditorium = auditoriumService.getById(id);

        ModelAndView modelAndView = new ModelAndView("auditoriums");
        modelAndView.addObject("auditoriums", Arrays.asList(auditorium));

        return modelAndView;
    }
}
