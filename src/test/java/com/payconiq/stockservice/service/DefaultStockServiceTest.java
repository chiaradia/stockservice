package com.payconiq.stockservice.service;

import com.payconiq.stockservice.datatransferobject.StockDTO;
import com.payconiq.stockservice.domainobject.Stock;
import com.payconiq.stockservice.exception.StockNotFoundException;
import com.payconiq.stockservice.repository.InMemoryStockRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.payconiq.stockservice.util.DataGenerator.DUMMY_STOCK_DTO_WITH_ID;
import static com.payconiq.stockservice.util.DataGenerator.DUMMY_STOCK_DTO_WITH_UPDATED_PRICE;
import static com.payconiq.stockservice.util.DataGenerator.DUMMY_STOCK_WITH_ID;
import static com.payconiq.stockservice.util.DataGenerator.DUMMY_STOCK_WITH_UPDATED_PRICE;
import static com.payconiq.stockservice.util.DataGenerator.PRICE_UPDATE_DTO;
import static com.payconiq.stockservice.util.DataGenerator.STOCK_CREATE_DTO;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultStockServiceTest
{

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    InMemoryStockRepository inMemoryStockRepository;

    private StockService stockService;


    @Before
    public void setUp()
    {
        this.stockService = new DefaultStockService(inMemoryStockRepository);
    }


    @Test
    public void givenAStockToBeCreated_whenCreated_itReturnsSameStockWithId()
    {
        when(inMemoryStockRepository.save(any(Stock.class)))
            .thenReturn(DUMMY_STOCK_WITH_ID);

        StockDTO createdStock = stockService.createStock(STOCK_CREATE_DTO);

        assertThat(createdStock, equalTo(DUMMY_STOCK_DTO_WITH_ID));
    }


    @Test
    public void givenARegisteredStockId_whenQueryById_registeredStockIsReturned()
    {
        when(inMemoryStockRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(DUMMY_STOCK_WITH_ID));

        StockDTO stock = stockService.getStockById(DUMMY_STOCK_WITH_ID.getId());

        assertThat(stock, equalTo(DUMMY_STOCK_DTO_WITH_ID));
    }

    @Test
    public void givenARequest_whenFetchAll_listOfStockDTOReturns()
    {
        when(inMemoryStockRepository.findAll())
            .thenReturn(Collections.singletonList(DUMMY_STOCK_WITH_ID));

        List<StockDTO> stockDTOList = stockService.getAll();

        assertThat(stockDTOList, notNullValue());
        assertThat(stockDTOList, not(stockDTOList.isEmpty()));
    }

    @Test
    public void givenANotRegisteredStockId_whenQueryById_shouldThrowException()
    {
        expectedException.expect(StockNotFoundException.class);
        stockService.getStockById(98L);
    }


    @Test
    public void givenARegisteredStock_whenPriceIsUpdated_changesAreSaved()
    {
        when(inMemoryStockRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(DUMMY_STOCK_WITH_ID));

        when(inMemoryStockRepository.update(any(Stock.class)))
            .thenReturn(DUMMY_STOCK_WITH_UPDATED_PRICE);

        StockDTO stock = stockService.updatePrice(DUMMY_STOCK_WITH_ID.getId(), PRICE_UPDATE_DTO);

        assertThat(stock, equalTo(DUMMY_STOCK_DTO_WITH_UPDATED_PRICE));
    }

    @Test
    public void givenARegisteredStock_whenDeleted_allOk()
    {
        when(inMemoryStockRepository.findById(any(Long.class)))
            .thenReturn(Optional.of(DUMMY_STOCK_WITH_ID));

        stockService.deleteStock(DUMMY_STOCK_WITH_ID.getId());

        verify(inMemoryStockRepository, times(1)).delete(DUMMY_STOCK_WITH_ID.getId());
    }
}