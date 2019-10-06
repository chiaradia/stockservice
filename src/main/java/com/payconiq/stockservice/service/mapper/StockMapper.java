package com.payconiq.stockservice.service.mapper;

import com.payconiq.stockservice.datatransferobject.StockDTO;
import com.payconiq.stockservice.domainobject.Stock;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StockMapper
{
    private StockMapper()
    {
    }


    public static StockDTO makeStockDTO(Stock stock)
    {
        return StockDTO.builder()
            .id(stock.getId())
            .currentPrice(PriceMapper.makePriceDto(stock.getCurrentPrice()))
            .dateCreated(stock.getDateCreated())
            .dateLastUpdated(stock.getDateLastUpdated())
            .name(stock.getName())
            .build();
    }


    public static List<StockDTO> makeStockDTOList(Collection<Stock> stocks)
    {
        return stocks.stream()
            .map(StockMapper::makeStockDTO)
            .collect(Collectors.toList());
    }


    public static Stock makeStock(StockDTO stockDTO)
    {
        return Stock.builder()
            .id(stockDTO.getId())
            .currentPrice(PriceMapper.makePrice(stockDTO.getCurrentPrice()))
            .dateCreated(stockDTO.getDateCreated())
            .dateLastUpdated(stockDTO.getDateLastUpdated())
            .name(stockDTO.getName())
            .build();
    }
}
