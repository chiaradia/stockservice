package com.payconiq.stockservice.service;

import com.payconiq.stockservice.datatransferobject.StockDTO;
import com.payconiq.stockservice.repository.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultStockService implements StockService
{
    private final Stock stockRepository;


    @Autowired
    public DefaultStockService(final Stock stockRepository)
    {
        this.stockRepository = stockRepository;
    }


    @Override
    public StockDTO getAll()
    {
        return null;
    }
}
