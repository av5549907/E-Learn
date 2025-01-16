package com.elearn.app.exceptions;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


public class ResourceNotFoundException extends  RuntimeException {

    public ResourceNotFoundException(){
        super("Resource not Found !!");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }


}
