package com.epam.spring.core.web;

import com.epam.spring.core.web.converter.DateTimeConverter;
import com.epam.spring.core.web.converter.TicketCollectionToPdfConverter;
import com.epam.spring.core.web.converter.TicketToPdfConverter;
import com.epam.spring.core.web.pdf.PdfCreator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.text.SimpleDateFormat;
import java.util.List;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.epam.spring.core.web")
public class WebConfig extends WebMvcConfigurerAdapter {
    private static final String DATE_FORMAT = "yyyy-mm-dd HH:mm";

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }

    @Bean
    public FreeMarkerViewResolver freemarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");

        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("/WEB-INF/views/ftl/");

        return freeMarkerConfigurer;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        commonsMultipartResolver.setMaxUploadSize(1000000);

        return commonsMultipartResolver;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Hibernate4Module());

        return objectMapper;
    }

    @Bean
    public PdfCreator pdfCreator() {
        return new PdfCreator();
    }

    @Bean
    public TicketToPdfConverter ticketToPdfConverter() {
        return new TicketToPdfConverter(pdfCreator());
    }

    @Bean
    public TicketCollectionToPdfConverter ticketCollectionToPdfConverter() {
        return new TicketCollectionToPdfConverter(pdfCreator());
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true).dateFormat(new SimpleDateFormat(DATE_FORMAT));
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        converters.add(ticketToPdfConverter());
        converters.add(ticketCollectionToPdfConverter());
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DateTimeConverter(DATE_FORMAT));
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {
        contentNegotiationConfigurer.favorPathExtension(false);
    }
}
