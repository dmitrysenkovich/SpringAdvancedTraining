package com.epam.spring.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// AccessDeniedException is not handled
// by any ExceptionHandler even when
// throwing from AccessDeniedHandler
@Controller
public class ErrorController {
    @RequestMapping(value = "/403", method = RequestMethod.GET)
    public String accessDenied(){
        return "accessDenied";
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String notFound(){
        return "notFound";
    }
}
