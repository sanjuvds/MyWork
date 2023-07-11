package com.example.metrictoimperial.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

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
public class UnitConverter {
	
	public static Object getFromDataValidated(String from) throws IncompatibleUnitTypesException {
		Object obj_from="";
		if (from == null || from == "") {
			obj_from = "Unknown/blank From and To type".toString();
		} else if (from.equalsIgnoreCase("kilos") || from.equalsIgnoreCase("kilo") || from.equals("kilogram") || from.equalsIgnoreCase("kgs") || from.equalsIgnoreCase("kilograms") ) {
		        obj_from = UnitConverter.Unit.KILOGRAM;
	    } else if (from.equalsIgnoreCase("pounds") || from.equalsIgnoreCase("lbs") || from.equalsIgnoreCase("pound")) {
	        obj_from = UnitConverter.Unit.POUND;
	    } else if (from.equalsIgnoreCase("°F") || from.equalsIgnoreCase("fahrenheit") || from.equalsIgnoreCase("farenheit")) {
	        obj_from = UnitConverter.Unit.FAHRENHEIT;
	    } else if (from.equalsIgnoreCase("celsius") || from.equalsIgnoreCase("°C")) {
	        obj_from = UnitConverter.Unit.CELSIUS;
	    } else if (from.equalsIgnoreCase("kilometres") || from.equalsIgnoreCase("kilometers")|| from.equalsIgnoreCase("kms") || from.equalsIgnoreCase("kilometre") || from.equalsIgnoreCase("kilometer")) {
	        obj_from = UnitConverter.Unit.KILOMETRE;
	    } else if (from.equalsIgnoreCase("mile") || from.equalsIgnoreCase("ml")|| from.equalsIgnoreCase("miles") || from.equalsIgnoreCase("mis")) {
	        obj_from = UnitConverter.Unit.MILE;
	    } else {
	    	obj_from = "Unknown/blank From and To type".toString();
	    }
		return obj_from;
	}

	public static Object getToDataValidated(String to) throws IncompatibleUnitTypesException {
		Object obj_to = "";
		if (to == null || to == "") {
			obj_to = "Unknown/blank From and To type".toString();
		} else if (to.equalsIgnoreCase("kilos") || to.equalsIgnoreCase("kilo") || to.equalsIgnoreCase("kilogram") || to.equalsIgnoreCase("kgs") || to.equalsIgnoreCase("kilograms") ) {
	        obj_to = UnitConverter.Unit.KILOGRAM;
	    } else if (to.equalsIgnoreCase("pounds") || to.equalsIgnoreCase("lbs") || to.equalsIgnoreCase("pound")) {
	        obj_to = UnitConverter.Unit.POUND;
	    } else if (to.equalsIgnoreCase("°F") || to.equalsIgnoreCase("fahrenheit") || to.equalsIgnoreCase("farenheit")) {
	        obj_to = UnitConverter.Unit.FAHRENHEIT;
	    } else if (to.equalsIgnoreCase("celsius") || to.equalsIgnoreCase("°C")) {
	        obj_to = UnitConverter.Unit.CELSIUS;
	    } else if (to.equalsIgnoreCase("kilometres") || to.equalsIgnoreCase("kilometers")|| to.equalsIgnoreCase("kms") || to.equalsIgnoreCase("kilometre") || to.equalsIgnoreCase("kilometer")) {
	        obj_to = UnitConverter.Unit.KILOMETRE;
	    } else if (to.equalsIgnoreCase("mile") || to.equalsIgnoreCase("ml")|| to.equalsIgnoreCase("miles") || to.equalsIgnoreCase("mis")) {
	        obj_to = UnitConverter.Unit.MILE;
	    } else {
	    	obj_to = "Unknown/blank From and To type".toString();
	    }
		return obj_to;
	}
	
    public enum Unit{
        KELVIN(UnitType.TEMPERATURE,"K",UnitSystem.METRIC,"Kelvin",new Aliases("°k","kelvin")),
        KILOGRAM(UnitType.MASS,"kg",UnitSystem.METRIC,"Kilogram",new Aliases("kilos","kilo","kilogram","kgs","kilograms")),
        METRE(UnitType.LENGTH,"m",UnitSystem.METRIC, "Metre",new Aliases("meter","meters","metres","metre")),
        FAHRENHEIT(UnitType.TEMPERATURE,"F",UnitSystem.IMPERIAL,"Fahrenheit",Unit.KELVIN, amount -> (5.0d/9.0d*(amount-32.0d)) + 273.0d,
                amount -> (9.0d/5.0d)*(amount-273.0d)+32.0d,new Aliases("°F","fahrenheit","farenheit")),
        CELSIUS(UnitType.TEMPERATURE, "C", UnitSystem.METRIC, "Celsius", Unit.KELVIN, amount -> amount + 273.15d, amount -> amount-273.15d,
                new Aliases("°C","celsius")),
        POUND(UnitType.MASS,"lb",UnitSystem.IMPERIAL,"Pound", Unit.KILOGRAM, 0.453592d, 2.20462d, new Aliases("pounds","lbs","pound")),
        MILE(UnitType.LENGTH,"mi",UnitSystem.IMPERIAL,"Mile", Unit.METRE, 1609.34d, 0.000621371d, new Aliases("mile","ml","miles","mis")),
        KILOMETRE(UnitType.LENGTH,"km",UnitSystem.METRIC,"Kilometre", Unit.METRE, 1000.0d, 0.001d, new Aliases("kilometres","kilometers","kms","kilometre","kilometer")),
        ;

        static {
            KILOGRAM.equivalent = POUND;
            POUND.equivalent = KILOGRAM;
            
            FAHRENHEIT.equivalent = CELSIUS;
            CELSIUS.equivalent = FAHRENHEIT;
            
            KILOMETRE.equivalent = MILE;
            MILE.equivalent = KILOMETRE;
        }

        private UnitType type;
        private String unit;
        private UnitSystem system;
        private String name;
        private Unit reference;
        private Converter to;
        private Converter from;
        private boolean base;
        private Aliases aliases;
        private Unit equivalent;

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         * @param multtoref the amount to multiply the amount with to convert it to the base unit
         */
        Unit(UnitType type, String unit, UnitSystem system, String name, Unit reference, double multtoref, double multfromref, Aliases aliases){
            this.type = type;
            this.unit = unit;
            this.system = system;
            this.name = name;
            this.reference = reference;
            this.to = new MultiplicationConverter(multtoref);
            this.from = new MultiplicationConverter(multfromref);
            base = false;
            this.aliases = aliases;
        }

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         * @param multtoref the amount to multiply the amount with to convert it to the base unit
         */
        Unit(UnitType type, String unit, UnitSystem system, String name, Unit reference, double multtoref, double multfromref){
            this.type = type;
            this.unit = unit;
            this.system = system;
            this.name = name;
            this.reference = reference;
            this.to = new MultiplicationConverter(multtoref);
            this.from = new MultiplicationConverter(multfromref);
            base = false;
            this.aliases = null;
        }

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         */
        Unit(UnitType type, String unit, UnitSystem system, String name, Unit reference, Converter converterto, Converter converterfrom){
            this.type = type;
            this.unit = unit;
            this.system = system;
            this.name = name;
            this.reference = reference;
            this.to = converterto;
            this.from = converterfrom;
            base = false;
            aliases = null;
        }

        /**
         * Creates a new Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         * @param reference the base unit of this category to convert to
         */
        Unit(UnitType type, String unit, UnitSystem system, String name, Unit reference, Converter converterto, Converter converterfrom, Aliases aliases){
            this.type = type;
            this.unit = unit;
            this.system = system;
            this.name = name;
            this.reference = reference;
            this.to = converterto;
            this.from = converterfrom;
            base = false;
            this.aliases = aliases;
        }

        /**
         * Creates a new Base Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         */
        Unit(UnitType type, String unit, UnitSystem system, String name){
            this.type = type;
            this.unit = unit;
            this.system = system;
            this.name = name;
            this.reference = null;
            this.to = null;
            this.from = null;
            base = true;
            aliases = null;
        }

        /**
         * Creates a new Base Unit
         * @param type unit category
         * @param unit short name for this unit
         * @param system unit system to use (imperial/metric)
         * @param name unit name
         */
        Unit(UnitType type, String unit, UnitSystem system, String name, Aliases aliases){
            this.type = type;
            this.unit = unit;
            this.system = system;
            this.name = name;
            this.reference = null;
            this.to = null;
            this.from = null;
            base = true;
            this.aliases = aliases;
        }

        public UnitType getType(){
            return type;
        }

        public String getSymbol() {
            return unit;
        }

        public UnitSystem getSystem() {
            return system;
        }

        public String getName() {
            return name;
        }

        public Unit getReference() {
            return reference;
        }

        public Converter getTo() {
            return to;
        }

        public Converter getFrom() {
            return from;
        }

        public boolean isBase() {
            return base;
        }

        public static Unit getBySymbol(String sym){
            for(Unit unit : Unit.values()){
                if(unit.getSymbol().equalsIgnoreCase(sym)) return unit;
            }
            return null;
        }

        public static Collection<Unit> getByType(UnitType type){
            Collection<Unit> col = new ArrayList<>();
            for(Unit unit : Unit.values()){
                if(unit.getType().equals(type)) col.add(unit);
            }
            return col;
        }

        public static Unit getByAlias(String alias){
            for(Unit unit : Unit.values()){
                if(unit.hasAlias(alias.toLowerCase())) return unit;
            }
            return null;
        }

        public static Unit getByTyped(String typed){
            Unit unit = getBySymbol(typed);
            if(unit!=null) return unit;
            unit = getByAlias(typed);
            if(unit!=null) return unit;
            return null;
        }

        public boolean hasAlias(String al){
            if(aliases==null) return false;
            else return aliases.contains(al);
        }

        public Unit getEquivalent() {
            return equivalent;
        }
    }

    public enum UnitType {
        TEMPERATURE("Temperature"),
        LENGTH("Length"),
        AREA("Area"),
        VOLUME("Volume"),
        MASS("Mass"),
        ;

        private String name;

        UnitType(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public enum UnitSystem {
        IMPERIAL("Imperial"),
        METRIC("Metric"),
        ;

        private String name;

        UnitSystem(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static class Aliases{

        String[] aliases;

        public Aliases(String... aliases) {
            this.aliases = aliases;
        }

        public String[] getAliases() {
            return aliases;
        }

        public Collection<String> getAliasesCollection() {
            return Arrays.asList(aliases);
        }

        public boolean contains(String text){
            return getAliasesCollection().contains(text);
        }
    }

    // METHODS
    /**
     * Convert a unit to another
     * @param amount the number
     * @param from the unit the number is in
     * @param to the unit the number should be converted to
     * @return the converted amount
     * @throws IncompatibleUnitTypesException when the units have different UnitTypes (are not from the same category)
     */
    public static double convert(double amount, Unit from, Unit to) throws IncompatibleUnitTypesException{

        if(!from.getType().equals(to.getType())){
            throw new IncompatibleUnitTypesException();
        }

        double based;
        if(from.isBase()){
            based = amount;
        }
        else {
            based = from.getTo().convert(amount);
        }

        double result;
        if(to.isBase()){
            result = based;
        }
        else {
            result = to.getFrom().convert(based);
        }

        return result;

    }

    // CONVERTERS

    private interface Converter {
        double convert(double amount);
    }

    private static class MultiplicationConverter implements Converter{

        private double fac;

        public MultiplicationConverter(double factor){
            this.fac = factor;
        }

        @Override
        public double convert(double amount) {
            return amount*fac;
        }

    }
}