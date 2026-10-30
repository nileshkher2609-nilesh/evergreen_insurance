package com.example.funeral_platform.exception;

public class PolicyNotFoundException extends RuntimeException{

    public PolicyNotFoundException(String message){
        super(message);
    }
}
