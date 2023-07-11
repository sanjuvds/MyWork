package com.example.metrictoimperial.service;

import org.springframework.stereotype.Component;

import com.example.metrictoimperial.exception.IncompatibleUnitTypesException;


/**
 * This a utility to convert units
 *
 * Usage:
 * <pre>
 * {@code
 * double result = UsageConverter.convert(double amount, Unit unitfrom, Unit to);
 * }
 * </pre>
 *
 * Units are only convertable to other units in the same category (UnitType)
 * 
 * @author fusionlightcat / Enveed / Arthur Schüler (C) 2017
 * 
 * You are free to use this file in any project, as long you do not claim it as your own or remove this notice.
 */
@Component
public interface UnitConverterService {
	
	public String get_converted_data(Double number, String from, String to) throws IncompatibleUnitTypesException;
}