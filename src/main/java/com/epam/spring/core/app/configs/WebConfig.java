package com.epam.spring.core.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"com.epam.spring.core.web", "com.epam.spring.core.web.exception"})
public class WebConfig extends WebMvcConfigurerAdapter {

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
	
}
