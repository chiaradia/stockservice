package com.payconiq.stockservice.repository;

import com.payconiq.stockservice.domainobject.Stock;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.payconiq.stockservice.DataGenerator.DUMMY_STOCK_WITHOUT_ID;
import static com.payconiq.stockservice.DataGenerator.DUMMY_STOCK_WITH_UPDATED_PRICE;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;

@RunWith(SpringJUnit4ClassRunner.class)
public class InMemoryStockRepositoryTest
{

    private InMemoryStockRepository stockRepository;


    @Before
    public void setUp()
    {
        this.stockRepository = new InMemoryStockRepository();
        stockRepository.init();
    }


    @Test
    public void givenAStock_whenIsSaved_canBeQueriedAndItHasAnId()
    {
        Stock stock = stockRepository.save(DUMMY_STOCK_WITHOUT_ID);
        Stock stockFound = stockRepository.findById(stock.getId()).orElse(null);

        assertThat(stock, equalTo(stockFound));
    }


    @Test
    public void givenAPersistedStock_whenQueried_stockObjectIsCorrect() throws Exception
    {
        Stock stock = stockRepository.findById(1L)
            .orElseThrow(Exception::new);

        assertThat(stock.getId(), notNullValue());
    }


    @Test
    public void givenAPersistedStock_whenPriceUpdated_newStockPriceIsCorrect() throws Exception
    {
        Stock persisted = stockRepository.findById(DUMMY_STOCK_WITH_UPDATED_PRICE.getId()).orElseThrow(Exception::new);

        Stock updated = stockRepository.update(DUMMY_STOCK_WITH_UPDATED_PRICE);

        assertThat(persisted.getId(), equalTo(updated.getId()));
        assertThat(updated.getCurrentPrice(), equalTo(DUMMY_STOCK_WITH_UPDATED_PRICE.getCurrentPrice()));

    }


    @Test
    public void givenAPersistedStock_whenDeleted_thenStockNoLongerExists()
    {
        Stock stock = stockRepository.save(DUMMY_STOCK_WITHOUT_ID);

        stockRepository.delete(stock.getId());
        Stock nullStock = stockRepository.findById(stock.getId()).orElse(null);

        assertThat(nullStock, nullValue());
    }


    @Test
    public void givenAStockRepository_whenFindAll_listIsNotEmpty()
    {
        List<Stock> stockList = new ArrayList<>(stockRepository.findAll());

        assertThat(stockList, notNullValue());
        assertThat(stockList, not(stockList.isEmpty()));
    }

}