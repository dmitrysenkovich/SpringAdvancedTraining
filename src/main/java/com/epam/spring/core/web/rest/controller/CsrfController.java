package com.epam.spring.core.web.rest.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class CsrfController {
    private static final Log LOGGER = LogFactory.getLog(CsrfController.class);

    @RequestMapping(value = "/api/csrf", method = RequestMethod.GET)
    public String csrf(HttpServletRequest httpServletRequest) {
        return ((CsrfToken) httpServletRequest.getAttribute(CsrfToken.class.getName())).getToken();
    }
}
