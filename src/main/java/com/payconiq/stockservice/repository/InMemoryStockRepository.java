package com.payconiq.stockservice.repository;

import com.payconiq.stockservice.domainobject.Price;
import com.payconiq.stockservice.domainobject.Stock;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class InMemoryStockRepository implements StockRepository
{
    private final AtomicLong stockId = new AtomicLong(0);
    private final Map<Long, Stock> stocks = new LinkedHashMap<>();


    @PostConstruct
    public void init()
    {
        stocks.put(stockId.incrementAndGet(), Stock
            .builder()
            .id(stockId.get())
            .currentPrice(Price.builder().value(BigDecimal.valueOf(134.45)).currency("EUR").build())
            .name("AMAZON")
            .dateCreated(OffsetDateTime.now())
            .dateLastUpdated(OffsetDateTime.now())
            .build());
        stocks.put(stockId.incrementAndGet(), Stock
            .builder()
            .id(stockId.get())
            .currentPrice(Price.builder().value(BigDecimal.valueOf(345.22)).currency("EUR").build())
            .name("GOOGLE")
            .dateCreated(OffsetDateTime.now())
            .dateLastUpdated(OffsetDateTime.now())
            .build());
    }


    @Override
    public Collection<Stock> findAll()
    {
        return stocks.values();
    }


    @Override
    public Stock findById(final Long id)
    {
        return stocks.get(id);
    }


    @Override
    public Stock save(final Stock stock)
    {
        Stock enhancedStockObject = enhanceStockObject(stock);
        stocks.put(enhancedStockObject.getId(), enhancedStockObject);
        return enhancedStockObject;
    }

    // This is necessary because the method replace() returns the currentValue, not the new one.
    @Override
    public Stock update(final Stock stock)
    {
        stocks.replace(stock.getId(), stock);
        return stocks.get(stock.getId());
    }


    private Stock enhanceStockObject(final Stock stock)
    {
        Long id = stockId.incrementAndGet();
        return stock
            .toBuilder()
            .id(id)
            .dateCreated(OffsetDateTime.now())
            .build();
    }
}
