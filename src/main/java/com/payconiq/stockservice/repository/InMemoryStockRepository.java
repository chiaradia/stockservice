package com.payconiq.stockservice.repository;

import com.payconiq.stockservice.domainobject.Stock;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryStockRepository implements StockRepository
{

    @Override
    public List<Stock> findAll()
    {
        return null;
    }


    @Override
    public Stock findById(Long id)
    {
        return null;
    }


    @Override
    public Stock save(Stock stock)
    {
        return null;
    }


    @Override
    public Stock update(Stock stock)
    {
        return null;
    }
}
