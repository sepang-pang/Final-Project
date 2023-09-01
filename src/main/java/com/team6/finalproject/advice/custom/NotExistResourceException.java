package com.team6.finalproject.advice.custom;

public class NotExistResourceException extends Exception {
    public NotExistResourceException(String message) {
        super(message);
    }
}
