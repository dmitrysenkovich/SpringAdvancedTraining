package com.epam.spring.core.web.controllers;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.spring.core.domain.Auditorium;
import com.epam.spring.core.service.IAuditoriumService;

@Controller
@RequestMapping("/auditorium")
public class AuditoriumController {
	
	private static final String AUDITORIUMS_VIEW = "auditorium/auditoriums_view";
	private static final String AUDITORIUM_ACTION_VIEW = "auditorium/auditorium_action_view";

	@Autowired
	private IAuditoriumService auditoriumService;
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAuditoriumByName(@RequestParam("name") String auditoriumName) {
		Auditorium auditorium = auditoriumService.getByName(auditoriumName);
		
		ModelAndView auditoriumsView = new ModelAndView(AUDITORIUMS_VIEW);
		auditoriumsView.addObject("auditoriums", Arrays.asList(auditorium));
		return auditoriumsView;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ModelAndView getAllAuditorium(@RequestParam("id") long id) {
		Auditorium auditorium = auditoriumService.getById(id);
		
		ModelAndView auditoriumsView = new ModelAndView(AUDITORIUMS_VIEW);
		auditoriumsView.addObject("auditoriums", Arrays.asList(auditorium));
		return auditoriumsView;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView getAllAuditoriums() {
		Collection<Auditorium> allAuditoriums = auditoriumService.getAll();
		
		ModelAndView allAuditoriumsView = new ModelAndView(AUDITORIUMS_VIEW);
		allAuditoriumsView.addObject("auditoriums", allAuditoriums);
		return allAuditoriumsView;
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public ModelAndView removeAuditorium(@RequestParam("id") long id) {
		Auditorium auditoriumToDelete = new Auditorium();
		auditoriumToDelete.setId(id);
		auditoriumService.remove(auditoriumToDelete);
		
		ModelAndView actionView = new ModelAndView(AUDITORIUM_ACTION_VIEW);
		actionView.addObject("action", "removing");
		return actionView;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView saveAuditorium(@RequestBody() Auditorium auditoriumToSave) {
		auditoriumService.save(auditoriumToSave);
		
		ModelAndView actionView = new ModelAndView(AUDITORIUM_ACTION_VIEW);
		actionView.addObject("action", "persisting");
		return actionView;
	}
	
}
