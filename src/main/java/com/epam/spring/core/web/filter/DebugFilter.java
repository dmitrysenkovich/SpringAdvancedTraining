package com.epam.spring.core.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter("/*")
public class DebugFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
        System.out.println("init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("asd");
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("asd");
    }
}
