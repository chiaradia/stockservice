package com.payconiq.stockservice.exception;

import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleException(Exception ex)
    {
        //log.error(ex);
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), OffsetDateTime.now()), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StockNotFoundException.class)
    public ResponseEntity handleStockNotFoundException(StockNotFoundException ex)
    {
        //log.error(ex);
        return new ResponseEntity<>(new ErrorMessage(ex.getMessage(), OffsetDateTime.now()), HttpStatus.NOT_FOUND);
    }


}
