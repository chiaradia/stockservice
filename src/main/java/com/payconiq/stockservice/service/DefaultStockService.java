package com.payconiq.stockservice.service;

import com.payconiq.stockservice.datatransferobject.PriceDTO;
import com.payconiq.stockservice.datatransferobject.StockDTO;
import com.payconiq.stockservice.domainobject.Price;
import com.payconiq.stockservice.domainobject.Stock;
import com.payconiq.stockservice.exception.StockNotFoundException;
import com.payconiq.stockservice.repository.StockRepository;
import com.payconiq.stockservice.service.mapper.PriceMapper;
import com.payconiq.stockservice.service.mapper.StockMapper;
import java.time.OffsetDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultStockService implements StockService
{
    private final StockRepository stockRepository;


    @Autowired
    public DefaultStockService(final StockRepository stockRepository)
    {
        this.stockRepository = stockRepository;
    }


    @Override
    public List<StockDTO> getAll()
    {
        return StockMapper.makeStockDTOList(stockRepository.findAll());
    }


    @Override
    public StockDTO getStockById(final Long id)
    {
        return StockMapper.makeStockDTO(findStockChecked(id));
    }


    @Override
    public StockDTO createStock(final StockDTO stockDTO)
    {
        Stock savedStock = stockRepository.save(StockMapper.makeStock(stockDTO));
        return StockMapper.makeStockDTO(savedStock);
    }


    @Override
    public StockDTO updatePrice(final Long id, final PriceDTO priceDTO)
    {
        Stock updatedStock = stockRepository.update(makeStockWithNewPrice(findStockChecked(id), PriceMapper.makePrice(priceDTO)));
        return StockMapper.makeStockDTO(updatedStock);

    }


    @Override
    public void deleteStock(Long id)
    {
        stockRepository.delete(findStockChecked(id).getId());
    }


    private Stock findStockChecked(final Long stockId)
    {
        return stockRepository.findById(stockId).orElseThrow(() -> new StockNotFoundException("Could not find stock with id: " + stockId));
    }


    private Stock makeStockWithNewPrice(final Stock stock, final Price price)
    {
        return Stock
            .builder()
            .id(stock.getId())
            .dateLastUpdated(OffsetDateTime.now())
            .dateCreated(stock.getDateCreated())
            .currentPrice(price)
            .name(stock.getName())
            .build();
    }


}
