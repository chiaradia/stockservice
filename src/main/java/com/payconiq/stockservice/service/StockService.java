package com.payconiq.stockservice.service;

import com.payconiq.stockservice.datatransferobject.StockDTO;
import java.util.List;

public interface StockService
{
    List<StockDTO> getAll();

    StockDTO getStockById(Long id);

    StockDTO createStock(StockDTO stockDTO);
}
