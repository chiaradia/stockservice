package com.payconiq.stockservice.controller;

import com.payconiq.stockservice.datatransferobject.StockDTO;
import com.payconiq.stockservice.service.StockService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


    @GetMapping
    public List<StockDTO> getStocks()
    {
        return stockService.getAll();
    }


    @GetMapping("/{stockId}")
    public StockDTO getStockById(@PathVariable("stockId") Long stockId)
    {
        return stockService.getStockById(stockId);
    }
}
