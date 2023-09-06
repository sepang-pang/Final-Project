package com.team6.finalproject.advice.custom;

public class InvalidAgeRangeException extends RuntimeException{
    public InvalidAgeRangeException(String message) {
        super(message);
    }
}
