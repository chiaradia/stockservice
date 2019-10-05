package com.payconiq.stockservice.controller;

import com.payconiq.stockservice.datatransferobject.PriceDTO;
import com.payconiq.stockservice.datatransferobject.StockDTO;
import com.payconiq.stockservice.service.StockService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StockDTO createStock(@RequestBody StockDTO stockDTO)
    {
        return stockService.createStock(stockDTO);
    }

    @DeleteMapping("/{stockId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStock(@PathVariable("stockId") Long stockId) {

    }

    @PutMapping("/{stockId}")
    public StockDTO updatePrice(@RequestBody PriceDTO priceDTO, @PathVariable("stockId") Long stockId)
    {
        return stockService.updatePrice(stockId, priceDTO);
    }
}
