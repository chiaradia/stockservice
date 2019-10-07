package com.payconiq.stockservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stockservice.datatransferobject.StockDTO;
import com.payconiq.stockservice.exception.StockNotFoundException;
import com.payconiq.stockservice.service.StockService;
import javax.servlet.ServletContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static com.payconiq.stockservice.util.DataGenerator.DUMMY_STOCK_DTO_WITHOUT_ID;
import static com.payconiq.stockservice.util.DataGenerator.DUMMY_STOCK_DTO_WITH_ID;
import static com.payconiq.stockservice.util.DataGenerator.DUMMY_STOCK_DTO_WITH_UPDATED_PRICE;
import static com.payconiq.stockservice.util.DataGenerator.PRICE_UPDATE_DTO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StockController.class)
public class StockControllerTest
{
    private static final String ENDPOINT = "/api/stocks/";

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @MockBean
    private StockService stockService;


    @Before
    public void setup()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    public void givenWac_whenServletContextStarts_thenItProvidesCarController()
    {
        ServletContext servletContext = wac.getServletContext();

        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        Assert.assertNotNull(wac.getBean("stockController"));
    }


    @Test
    public void givenACorrectRequest_whenPosted_thenStockIsReturnedWithId() throws Exception
    {

        given(stockService.createStock(any(StockDTO.class)))
            .willReturn(DUMMY_STOCK_DTO_WITH_ID);

        String stockJsonRequest = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(DUMMY_STOCK_DTO_WITHOUT_ID);

        this.mockMvc.perform(post(ENDPOINT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(stockJsonRequest)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print()).andExpect(status().isCreated())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.currentPrice").exists())
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.dateCreated").exists())
            .andExpect(jsonPath("$.currentPrice.value").value(DUMMY_STOCK_DTO_WITHOUT_ID.getCurrentPrice().getValue()))
            .andExpect(jsonPath("$.currentPrice.currency").value(DUMMY_STOCK_DTO_WITHOUT_ID.getCurrentPrice().getCurrency()))
            .andExpect(jsonPath("$.name").value(DUMMY_STOCK_DTO_WITHOUT_ID.getName()));

    }


    @Test
    public void givenAStockId_whenGetRequested_thenStockIsReturned() throws Exception
    {

        given(stockService.getStockById(DUMMY_STOCK_DTO_WITH_ID.getId()))
            .willReturn(DUMMY_STOCK_DTO_WITH_ID);

        this.mockMvc.perform(get(ENDPOINT + DUMMY_STOCK_DTO_WITH_ID.getId())
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.currentPrice").exists())
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.dateCreated").exists());

    }

    @Test
    public void givenANotRegisteredStockId_whenGetRequested_thenNotFound() throws Exception
    {

        given(stockService.getStockById(DUMMY_STOCK_DTO_WITH_ID.getId()))
            .willThrow(new StockNotFoundException("Message"));

        this.mockMvc.perform(get(ENDPOINT + DUMMY_STOCK_DTO_WITH_ID.getId())
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andDo(print()).andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.timeStamp").exists());

    }


    @Test
    public void givenAStockId_whenPriceUpdatedRequested_thenStockReturnedWithNewPrice() throws Exception
    {

        given(stockService.updatePrice(DUMMY_STOCK_DTO_WITH_ID.getId(), PRICE_UPDATE_DTO))
            .willReturn(DUMMY_STOCK_DTO_WITH_UPDATED_PRICE);

        String priceUpdateJsonRequest = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(PRICE_UPDATE_DTO);

        this.mockMvc.perform(put(ENDPOINT + DUMMY_STOCK_DTO_WITH_ID.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(priceUpdateJsonRequest)
            .accept(MediaType.APPLICATION_JSON_UTF8))
            .andDo(print()).andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.currentPrice").exists())
            .andExpect(jsonPath("$.name").exists())
            .andExpect(jsonPath("$.dateCreated").exists());

    }


    @Test
    public void givenAStockId_whenDeleteRequested_thenNoContent() throws Exception
    {

        this.mockMvc.perform(delete(ENDPOINT + DUMMY_STOCK_DTO_WITH_ID.getId())
            .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
            .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andDo(print()).andExpect(status().isNoContent());
    }
}