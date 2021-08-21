package dev.abel.springbootdocker.generics;

public class InvalidDataException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public InvalidDataException(String errorMessage) {
        super(errorMessage);
    }
}