package com.apap.tutorial4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

/***FlightController*/
@Controller
public class FlightController {
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value="/flight/add/{licenseNumber}", method=RequestMethod.GET)
	private String add(@PathVariable(value="licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@RequestMapping(value="/flight/add", method=RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
	
	@RequestMapping(value = "/flight/delete/{flightNumber}", method = RequestMethod.GET)
	private String deleteFlight(@PathVariable(value = "flightNumber") String flightNumber, Model model) {
		flightService.deleteFlight(flightNumber);
		return "delete-flight";
		
	}
	
	@RequestMapping(value = "/flight/update{flightNumber}", method = RequestMethod.GET)
	private String updateF(@PathVariable(value= "flightNumber") String flightNumber, Model model) {
		FlightModel oldFlight = flightService.getFlightDetailByFlightNumber(flightNumber);
		model.addAttribute("oldFlight", oldFlight);
		model.addAttribute("newFlight", new FlightModel());
		return "update-flight";
	}
	
	@RequestMapping(value = "/flight/update{flightNumber}", method = RequestMethod.POST)
	private String updateFlightSubmit(@ModelAttribute FlightModel newFlight, @PathVariable(value = "flightNumber") String flightNumber) {
		flightService.updateFlight(flightNumber, newFlight);
		return "update";
	}
	
	@RequestMapping(value ="/flight/viewall{flightNumber}", method = RequestMethod.GET)
	private String viewAllFlight(@PathVariable(value = "flightNumber") String flightNumber, Model model) {
		FlightModel flightModel = flightService.getFlightDetailByFlightNumber(flightNumber);
		model.addAttribute("flightNumber", flightModel.getFlightNumber());
		model.addAttribute("pilot", flightModel.getPilot());
		return "viewall";
	}
	

}
