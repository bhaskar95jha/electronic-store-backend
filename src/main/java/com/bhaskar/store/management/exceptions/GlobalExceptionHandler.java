package com.bhaskar.store.management.exceptions;

import com.bhaskar.store.management.dtos.ApiResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    //handler resource not found exception

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseMessage> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
        log.info("Resource Not found exception invoked");
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND).isSuccess(true).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponseMessage> dataIntegrityException(DataIntegrityViolationException exception){
        log.info("dataIntegrityException invoked");
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(exception.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).isSuccess(true).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,Object>> methodArgumentNotvalidException(MethodArgumentNotValidException exception){
        log.info("MethodArgumentNotValidException invoked");
        List<ObjectError> allErrors = exception.getBindingResult().getAllErrors();
        Map<String,Object>  response = new HashMap<>();

        allErrors.stream().forEach(
                objectError -> {
                    String message = objectError.getDefaultMessage();
                    String field = ((FieldError) objectError).getField();
                    response.put(field,message);
                }

                );

        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(PropertyReferenceException.class)
    public ResponseEntity<ApiResponseMessage> propertyReferenceException(PropertyReferenceException exception){
        log.info("PropertyReferenceException invoked");
        ApiResponseMessage response = ApiResponseMessage.builder().message(exception.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).isSuccess(true).build();
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadApiRequest.class)
    public ResponseEntity<ApiResponseMessage> badApiResponseHandler(BadApiRequest exception){
        log.info("BadApiRequest invoked");
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message(exception.getMessage()).status(HttpStatus.BAD_REQUEST).isSuccess(false).build();
        return new ResponseEntity<>(responseMessage,HttpStatus.BAD_REQUEST);
    }

}
