package com.payconiq.stockservice.service.mapper;

import com.payconiq.stockservice.datatransferobject.PriceDTO;
import com.payconiq.stockservice.domainobject.Price;

public class PriceMapper
{
    private PriceMapper()
    {

    }


    public static PriceDTO makePriceDto(Price price)
    {
        return PriceDTO
            .builder()
            .currency(price.getCurrency())
            .value(price.getValue())
            .build();
    }


    public static Price makePrice(PriceDTO priceDTO)
    {
        return Price
            .builder()
            .currency(priceDTO.getCurrency())
            .value(priceDTO.getValue())
            .build();
    }
}
