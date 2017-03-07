package com.epam.spring.core.web.rest.controller;

import com.epam.spring.core.domain.Event;
import com.epam.spring.core.domain.User;
import com.epam.spring.core.service.IEventService;
import com.epam.spring.core.service.IUserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController("restBatchLoadController")
@RequestMapping("/api/load")
public class BatchLoadController {
    private static final Log LOGGER = LogFactory.getLog(BatchLoadController.class);

    private final IEventService eventService;
    private final IUserService userService;

    private final ObjectMapper objectMapper;

    @Autowired
    public BatchLoadController(IEventService eventService, IUserService userService, ObjectMapper objectMapper) {
        this.eventService = eventService;
        this.userService = userService;
        this.objectMapper = objectMapper;
        LOGGER.info("Initializing is completed");
    }

    @RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String load(@RequestParam(value = "users", required = false) MultipartFile usersData,
                       @RequestParam(value = "events", required = false) MultipartFile eventsData) throws IOException {
        LOGGER.info("load POST request");

        if (usersData.getSize() != 0) {
            LOGGER.info("Users data is passed");

            String usersInString = IOUtils.toString(new ByteArrayInputStream(usersData.getBytes()), "UTF-8");
            LOGGER.info("Read users json data:" + usersInString);

            List<User> users = objectMapper.readValue(usersInString, new TypeReference<List<User>>(){});
            users.forEach(userService::save);
            LOGGER.info("Saved users");
        }
        if (eventsData.getSize() != 0) {
            LOGGER.info("Events data is passed");

            String eventsInString = IOUtils.toString(new ByteArrayInputStream(eventsData.getBytes()), "UTF-8");
            LOGGER.info("Read events json data:" + eventsInString);

            List<Event> events = objectMapper.readValue(eventsInString, new TypeReference<List<Event>>(){});
            eventService.save(events);
            LOGGER.info("Saved events");
        }

        if (usersData.getSize() == 0 && eventsData.getSize() == 0)
            throw new IllegalArgumentException("No data has been provided");

        return "Data was uploaded";
    }
}
