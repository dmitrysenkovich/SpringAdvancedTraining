package com.epam.spring.core.web.controller;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/load")
public class BatchLoadController {
    private static final Log LOGGER = LogFactory.getLog(BatchLoadController.class);

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String load() {
        LOGGER.info("load GET request");

        return "load";
    }
}
