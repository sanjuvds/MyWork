package com.example.metrictoimperial.exception;

public class IncompatibleUnitTypesException extends Exception{
    public IncompatibleUnitTypesException(String message) {
        super(message);
    }

    public IncompatibleUnitTypesException(){
        super();
    }
}

