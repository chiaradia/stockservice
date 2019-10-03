package com.payconiq.stockservice.controller;

import com.payconiq.stockservice.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stocks")
public class StockController
{
    private final StockService stockService;


    @Autowired
    public StockController(final StockService stockService)
    {
        this.stockService = stockService;
    }
}
