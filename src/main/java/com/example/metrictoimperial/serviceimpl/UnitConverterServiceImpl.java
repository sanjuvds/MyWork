package com.example.metrictoimperial.serviceimpl;

import org.springframework.stereotype.Component;

import com.example.metrictoimperial.exception.IncompatibleUnitTypesException;
import com.example.metrictoimperial.service.UnitConverterService;
import com.example.metrictoimperial.util.UnitConverter;
import com.example.metrictoimperial.util.UnitConverter.Unit;


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
public class UnitConverterServiceImpl implements UnitConverterService  {

	@Override
	public String get_converted_data(Double number, String from, String to) throws IncompatibleUnitTypesException {
		Double result = 0.0;
		Object obj_to = UnitConverter.getToDataValidated(to); 
		Object obj_from = UnitConverter.getFromDataValidated(from); 
		if (obj_to.equals("Unknown/blank From and To type") || obj_from.equals("Unknown/blank From and To type")){
			return "Unknown/blank From and To type".toString();
		} else {
			if (number != null) {
				result = UnitConverter.convert(number, (Unit) obj_from, (Unit) obj_to);
			} else {
				return "Unknown/blank number type".toString();
			}
		}
		return result.toString();
	}
	
	
}