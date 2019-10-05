package com.payconiq.stockservice.repository;

import com.payconiq.stockservice.domainobject.Stock;
import java.util.Collection;
import java.util.Optional;

public interface StockRepository
{
    Collection<Stock> findAll();

    Optional<Stock> findById(Long id);

    Stock save(Stock stock);

    Stock update(Stock stock);

    void delete(Long id);
}
