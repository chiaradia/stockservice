package com.payconiq.stockservice.repository;

import com.payconiq.stockservice.domainobject.Price;
import com.payconiq.stockservice.domainobject.Stock;
import com.payconiq.stockservice.exception.StockNotFoundException;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryStockRepository implements StockRepository
{
    private AtomicLong stockId = new AtomicLong(0);
    private List<Stock> stocks = new ArrayList<>(); //Change to MAP -> O(1) for search


    @PostConstruct
    public void init()
    {
        stocks.addAll(Arrays.asList(
            Stock
                .builder()
                .id(stockId.incrementAndGet())
                .currentPrice(Price.builder().value(BigDecimal.valueOf(134.45)).currency("EUR").build())
                .name("AMAZON")
                .dateCreated(OffsetDateTime.now())
                .dateLastUpdated(OffsetDateTime.now())
                .build(),
            Stock
                .builder()
                .id(stockId.incrementAndGet())
                .currentPrice(Price.builder().value(BigDecimal.valueOf(345.22)).currency("EUR").build())
                .name("GOOGLE")
                .dateCreated(OffsetDateTime.now())
                .dateLastUpdated(OffsetDateTime.now())
                .build()

        ));
    }


    @Override
    public List<Stock> findAll()
    {
        return stocks;
    }


    @Override
    public Stock findById(Long id)
    {
        return stocks
            .stream()
            .filter(stock -> id.equals(stock.getId()))
            .findFirst().orElseThrow(() -> new StockNotFoundException("Could not find stock with id: " + id));
    }


    @Override
    public Stock save(Stock stock)
    {
        //Since this is an immutable object, we need to create a copy to set the ID, dateCreated.
        Stock stockCopy = stock
            .toBuilder()
            .id(stockId.incrementAndGet())
            .name(stock.getName())
            .currentPrice(stock.getCurrentPrice())
            .dateCreated(OffsetDateTime.now())
            .build();

        stocks.add(stockCopy);
        return stockCopy;
    }


    @Override
    public Stock update(Stock stock)
    {
        Stock stockCopy = stock
            .toBuilder()
            .id(stock.getId())
            .name(stock.getName())
            .currentPrice(stock.getCurrentPrice())
            .dateCreated(stock.getDateCreated())
            .dateLastUpdated(OffsetDateTime.now())
            .build();

        stocks.add(stocks.indexOf(stock), stockCopy);
        return stockCopy;
    }
}
