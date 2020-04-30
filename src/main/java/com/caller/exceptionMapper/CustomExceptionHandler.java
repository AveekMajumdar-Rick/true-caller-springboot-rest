package com.caller.exceptionMapper;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.caller.constands.ErrorConstants;
import com.caller.model.ErrorResponse;

@SuppressWarnings({"unchecked","rawtypes"})
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler 
{
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(ErrorConstants.GEN_SERVER_ERROR, ex.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    @ExceptionHandler(RecordNotFoundException.class)
    public final ResponseEntity<Object> handleUserNotFoundException(RecordNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(ErrorConstants.NOT_FOUND, ex.getMessage());
        return new ResponseEntity(error, HttpStatus.NOT_FOUND);
    }
 
    @ExceptionHandler(RecordAlreadyExixts.class)
    public final ResponseEntity<Object> handleAlreadyExistsException(RecordNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(ErrorConstants.GEN_BAD_REQUEST, ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(UnAuthorizedException.class)
    public final ResponseEntity<Object> handleAuthorizationException(RecordNotFoundException ex, WebRequest request) {
        ErrorResponse error = new ErrorResponse(ErrorConstants.SC_UNAUTHORIZED, ex.getMessage());
        return new ResponseEntity(error, HttpStatus.UNAUTHORIZED);
    }
    
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorResponse error = new ErrorResponse(ErrorConstants.GEN_BAD_REQUEST, ex.getMessage());
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}