package com.payconiq.stockservice.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler
{

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity handleException(Exception ex)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(logAndConvert(ex));
    }


    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(logAndConvert(ex));
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity handleStockNotFoundException(StockNotFoundException ex)
    {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(logAndConvert(ex));
    }


    private ErrorMessage logAndConvert(Throwable throwable)
    {
        log.error("Error catch by the GlobalExceptionHandler", throwable);
        throwable.printStackTrace(new PrintWriter(new StringWriter()));
        return new ErrorMessage(throwable.getMessage(), OffsetDateTime.now());
    }


}
