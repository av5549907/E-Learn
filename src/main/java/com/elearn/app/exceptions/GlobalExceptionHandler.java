package com.elearn.app.exceptions;

import com.elearn.app.dtos.CustomMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<CustomMessage> handleResourceNotFound(ResourceNotFoundException resourceNotFoundException){
        CustomMessage customMessage=new CustomMessage();
        customMessage.setMessage(resourceNotFoundException.getMessage());
        customMessage.setSuccess(false);
      return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationError(MethodArgumentNotValidException ex){

        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldName=((FieldError)(error)).getField();
            String message=error.getDefaultMessage();
            errors.put(fieldName,message);
        });
        return  new ResponseEntity<>(errors,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<CustomMessage> handleResourceError(AuthorizationDeniedException ex){
        CustomMessage customMessage=new CustomMessage();
        customMessage.setSuccess(false);
        customMessage.setMessage(ex.getMessage());
        return new ResponseEntity<>(customMessage,HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<CustomMessage> handleResourceError(AccessDeniedException ex){
        CustomMessage customMessage=new CustomMessage();
        customMessage.setSuccess(false);
        customMessage.setMessage(ex.getMessage());
        return new ResponseEntity<>(customMessage,HttpStatus.UNAUTHORIZED);

    }


}
