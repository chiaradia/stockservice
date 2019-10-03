package com.payconiq.stockservice.service;

import com.payconiq.stockservice.datatransferobject.StockDTO;
import com.payconiq.stockservice.domainobject.Stock;
import com.payconiq.stockservice.repository.InMemoryStockRepository;
import com.payconiq.stockservice.service.mapper.StockMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultStockService implements StockService
{
    private final InMemoryStockRepository inMemoryStockRepository;


    @Autowired
    public DefaultStockService(final InMemoryStockRepository inMemoryStockRepository)
    {
        this.inMemoryStockRepository = inMemoryStockRepository;
    }


    @Override
    public List<StockDTO> getAll()
    {
        return StockMapper.makeStockDTOList(inMemoryStockRepository.findAll());
    }


    @Override
    public StockDTO getStockById(final Long id)
    {
        return StockMapper.makeStockDTO(inMemoryStockRepository.findById(id));
    }


    @Override
    public StockDTO createStock(StockDTO stockDTO)
    {
        Stock savedStock = inMemoryStockRepository.save(StockMapper.makeStock(stockDTO));
        return StockMapper.makeStockDTO(savedStock);
    }
}
