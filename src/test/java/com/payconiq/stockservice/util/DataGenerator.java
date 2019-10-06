package com.payconiq.stockservice.util;

import com.payconiq.stockservice.datatransferobject.PriceDTO;
import com.payconiq.stockservice.datatransferobject.StockDTO;
import com.payconiq.stockservice.domainobject.Price;
import com.payconiq.stockservice.domainobject.Stock;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

public class DataGenerator
{
    private DataGenerator()
    {
    }


    private static final OffsetDateTime DATE_CREATED = OffsetDateTime.now();
    private static final OffsetDateTime DATE_UPDATED = OffsetDateTime.now();

    public static final Stock DUMMY_STOCK_WITH_ID = Stock.builder()
        .id(1L)
        .dateCreated(DATE_CREATED)
        .currentPrice(Price.builder()
            .currency("EUR")
            .value(new BigDecimal(123.45))
            .build())
        .name("DUMMY STOCK")
        .build();

    public static final Stock DUMMY_STOCK_WITHOUT_ID = Stock.builder()
        .currentPrice(Price.builder()
            .currency("EUR")
            .value(new BigDecimal(123.45))
            .build())
        .name("DUMMY STOCK")
        .build();

    public static final Stock DUMMY_STOCK_WITH_UPDATED_PRICE = Stock.builder()
        .id(1L)
        .currentPrice(Price.builder()
            .currency("EUR")
            .value(new BigDecimal(300.00))
            .build())
        .name("DUMMY STOCK")
        .dateCreated(DATE_CREATED)
        .dateLastUpdated(DATE_UPDATED)
        .build();

    public static final StockDTO DUMMY_STOCK_DTO_WITH_UPDATED_PRICE = StockDTO.builder()
        .id(1L)
        .currentPrice(PriceDTO.builder()
            .currency("EUR")
            .value(new BigDecimal(300.00))
            .build())
        .name("DUMMY STOCK")
        .dateCreated(DATE_CREATED)
        .dateLastUpdated(DATE_UPDATED)
        .build();

    public static final StockDTO DUMMY_STOCK_DTO_WITH_ID = StockDTO.builder()
        .id(1L)
        .currentPrice(PriceDTO.builder()
            .currency("EUR")
            .value(new BigDecimal(123.45))
            .build())
        .name("DUMMY STOCK")
        .dateCreated(DATE_CREATED)
        .build();

    public static final StockDTO DUMMY_STOCK_DTO_WITHOUT_ID = StockDTO.builder()
        .currentPrice(PriceDTO.builder()
            .currency("EUR")
            .value(new BigDecimal(123.45))
            .build())
        .name("DUMMY STOCK")
        .build();

    public static final Price DUMMY_PRICE = Price.builder()
        .currency("EUR")
        .value(new BigDecimal(123.45))
        .build();

    public static final PriceDTO PRICE_UPDATE_DTO = PriceDTO.builder()
        .currency("EUR")
        .value(new BigDecimal(300.00))
        .build();

    public static final StockDTO STOCK_CREATE_DTO = StockDTO.builder()
        .name("DUMMY STOCK")
        .currentPrice(PriceDTO.builder()
            .currency("EUR")
            .value(new BigDecimal(123.45))
            .build())
        .build();

}
