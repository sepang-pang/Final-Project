package com.team6.finalproject.advice.custom;

public class SelfLikeNotAllowedException extends Exception {
    public SelfLikeNotAllowedException(String message) {
        super(message);
    }
}
