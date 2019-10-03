package com.payconiq.stockservice.repository;

import com.payconiq.stockservice.domainobject.Stock;
import java.util.List;

public interface StockRepository
{
    List<Stock> findAll();

    Stock findById(Long id);

    Stock save(Stock stock);

    Stock update(Stock stock);
}
