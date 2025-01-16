package com.elearn.app.exceptions;

import com.elearn.app.dtos.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomMessage> handleResourceNotFound(ResourceNotFoundException resourceNotFoundException){
        CustomMessage customMessage=new CustomMessage();
        customMessage.setMessage(resourceNotFoundException.getMessage());
        customMessage.setSuccess(false);
      return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
    }

}
