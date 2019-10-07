package com.payconiq.stockservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.stockservice.datatransferobject.PriceDTO;
import com.payconiq.stockservice.datatransferobject.StockDTO;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static com.payconiq.stockservice.util.DataGenerator.DUMMY_STOCK_DTO_WITHOUT_ID;
import static com.payconiq.stockservice.util.DataGenerator.DUMMY_STOCK_WITHOUT_ID;
import static com.payconiq.stockservice.util.DataGenerator.PRICE_UPDATE_DTO;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class StockControllerIT
{
    private static final String ENDPOINT = "/api/stocks/";
    private static final long STOCK_ID = 1L;
    private static final long WRONG_STOCK_ID = 5L;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;


    @Before
    public void setup()
    {
        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
    }


    @Test
    public void givenANotRegisteredStockId_whenGetRequested_thenNotFound()
    {
        given()
            .when()
            .get(ENDPOINT + WRONG_STOCK_ID)
            .then()
            .log().ifValidationFails()
            .statusCode(NOT_FOUND.value());
    }


    /*
     * Added the annotation to clear the cached application context, since this test is being executed after the one that tests the deletion.
     *
     * I'm aware that for this situation I could either change the ID or add the annotation. =)
     *
     * */
    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void givenARegisteredStockId_whenGetRequested_thenOk()
    {
        given()
            .when()
            .get(ENDPOINT + STOCK_ID)
            .then()
            .log().ifValidationFails()
            .statusCode(OK.value());
    }


    @Test
    public void givenARegisteredStockId_whenDeleteRequested_thenNoContent()
    {
        Long stockId = 1L;

        given()
            .when()
            .delete(ENDPOINT + stockId)
            .then()
            .log().ifValidationFails()
            .statusCode(NO_CONTENT.value());
    }


    @Test
    public void givenACorrectRequest_whenPosted_thenCreated() throws JsonProcessingException
    {

        given()
            .contentType(JSON)
            .body(createStockDtoJsonRequest(DUMMY_STOCK_DTO_WITHOUT_ID))
            .post(ENDPOINT)
            .then()
            .log().ifValidationFails()
            .statusCode(CREATED.value());
    }


    @Test
    public void givenAStockIdAndAPriceDTO_whenPriceUpdatedRequested_thenOk() throws JsonProcessingException
    {

        given()
            .contentType(JSON)
            .body(createPriceDtoJsonRequest(PRICE_UPDATE_DTO))
            .put(ENDPOINT + STOCK_ID)
            .then()
            .log().ifValidationFails()
            .statusCode(OK.value());
    }


    @Test
    public void givenAnIncorrectRequest_whenPosted_thenBadRequest() throws JsonProcessingException
    {

        String somethingWrong = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString("{\"namee\":\"" + DUMMY_STOCK_WITHOUT_ID.getName() + "\"}");

        given()
            .contentType(JSON)
            .body(somethingWrong)
            .post(ENDPOINT)
            .then()
            .log().ifValidationFails()
            .statusCode(BAD_REQUEST.value());
    }


    @Test
    public void givenARequestToGetAllStocks_whenGetRequested_thenOk()
    {

        given()
            .when()
            .get(ENDPOINT)
            .then()
            .log().ifValidationFails()
            .statusCode(OK.value())
            .contentType(JSON)
            .body(not(equalTo("[]")));
    }


    private String createStockDtoJsonRequest(StockDTO stockDTO) throws JsonProcessingException
    {
        return objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(stockDTO);
    }


    private String createPriceDtoJsonRequest(PriceDTO priceDTO) throws JsonProcessingException
    {
        return objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(priceDTO);
    }
}
