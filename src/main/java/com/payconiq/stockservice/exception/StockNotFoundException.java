package com.payconiq.stockservice.exception;

public class StockNotFoundException extends RuntimeException
{
    public StockNotFoundException(String message)
    {
        super(message);
    }
}