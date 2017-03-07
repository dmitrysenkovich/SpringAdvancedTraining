package com.epam.spring.core.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class InternalErrorExceptionHandler {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ModelAndView internalErrorExceptionHandler(HttpServletRequest request, Exception exception){
        ModelAndView modelAndView = new ModelAndView("internalError");
        modelAndView.addObject("message", exception.getMessage());
        exception.printStackTrace();

        return modelAndView;
    }
}
