package com.example.metrictoimperial.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.metrictoimperial.exception.IncompatibleUnitTypesException;
import com.example.metrictoimperial.service.UnitConverterService;

@RestController
public class MetricToImperialController {
	
	private UnitConverterService unitConverterService;

	public MetricToImperialController(UnitConverterService unitConverterService) {
		super();
		this.unitConverterService = unitConverterService;
	}
	
	@GetMapping("/get_converted_data")
	public String get_converted_data(@RequestParam(name = "number") Double number, @RequestParam(name = "from") String from, @RequestParam(name = "to") String to) throws IncompatibleUnitTypesException {
		return unitConverterService.get_converted_data(number, from, to).toString();
	}
}