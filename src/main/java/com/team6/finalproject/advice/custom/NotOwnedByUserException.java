package com.team6.finalproject.advice.custom;

public class NotOwnedByUserException extends Exception {
    public NotOwnedByUserException(String message) {
        super(message);
    }
}