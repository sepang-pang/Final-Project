package com.team6.finalproject.advice.custom;

public class CapacityFullException extends Exception {
    public CapacityFullException(String message) {
        super(message);
    }
}