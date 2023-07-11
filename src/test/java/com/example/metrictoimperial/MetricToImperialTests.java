package com.example.metrictoimperial;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.example.metrictoimperial.exception.IncompatibleUnitTypesException;
import com.example.metrictoimperial.service.UnitConverterService;
import com.example.metrictoimperial.serviceimpl.UnitConverterServiceImpl;

@SpringBootTest
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
class MetricToImperialTests {
		
	@Configuration
	static class ContextConfiguration {
	    @Bean
	    public UnitConverterService metric_to_imperial() {
	    	UnitConverterService unitConverterService = new UnitConverterServiceImpl();
	        return unitConverterService;
	    }
	    @Bean
	    public IncompatibleUnitTypesException incompatibleUnitTypesException() {
	    	IncompatibleUnitTypesException incompatibleUnitTypesException = new IncompatibleUnitTypesException();
	        return incompatibleUnitTypesException;
	    }
	}
	
	@Autowired
	UnitConverterService unitConverterService;

	@Autowired
	IncompatibleUnitTypesException incompatibleUnitTypesException;
	
	public Double number;
	
	public String from;
	
	public String to;
		
	@Test
	void check_number_not_null() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "Pound";
		to = "kilogram";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to).toString(), "0.907184".toString());
	}
	
	@Test
	void check_fromvalue_not_null() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = null;
		to = "kilogram";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to).toString(), "Unknown/blank From and To type".toString());
	}
	
	@Test
	void check_tovalue_not_null() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "Pound";
		to = null;
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to).toString(), "Unknown/blank From and To type".toString());
	}
	
	@Test
	void check_fromvalue_not_blank() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "";
		to = "kilogram";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from.toString(), to), "Unknown/blank From and To type".toString());
	}
	
	@Test
	void check_tovalue_not_blank() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "Pound";
		to = "";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to.toString()), "Unknown/blank From and To type".toString());
	}

	@Test
	void varify_conversion_of_kilogram_to_pound() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "kilogram";
		to = "Pound";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to).toString(), "4.40924".toString());
	}
	
	@Test
	void varify_conversion_of_FAHRENHEIT_to_CELSIUS() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "FAHRENHEIT";
		to = "CELSIUS";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to).toString(), "-16.816666666666663".toString());
	}

	@Test
	void varify_conversion_of_KILOMETRE_to_MILE() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "KILOMETRE";
		to = "MILE";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to).toString(), "1.242742".toString());
	}
	
	@Test
	void varify_conversion_of_pound_to_kilogram() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "pound";
		to = "kilogram";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to).toString(), "0.907184".toString());
	}

	@Test
	void varify_conversion_of_CELSIUS_to_FAHRENHEIT() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "CELSIUS";
		to = "FAHRENHEIT";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to).toString(), "35.86999999999996".toString());
	}

	@Test
	void varify_conversion_of_MILE_to_KILOMETRE() throws IncompatibleUnitTypesException {
		number = 2.0;
		from = "MILE";
		to = "KILOMETRE";
		Assertions.assertEquals(unitConverterService.get_converted_data(number, from, to).toString(), "3.21868".toString());
	}
}
