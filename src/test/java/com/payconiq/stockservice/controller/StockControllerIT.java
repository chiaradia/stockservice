package com.payconiq.stockservice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
        Long stockId = 5L;

        given()
            .when()
            .get(ENDPOINT + stockId)
            .then()
            .log().ifValidationFails()
            .statusCode(NOT_FOUND.value());
    }


    @Test
    public void givenARegisteredStockId_whenGetRequested_thenOk()
    {
        Long stockId = 1L;

        given()
            .when()
            .get(ENDPOINT + stockId)
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

        String stockJsonRequest = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(DUMMY_STOCK_DTO_WITHOUT_ID);

        given()
            .contentType(JSON)
            .body(stockJsonRequest)
            .post(ENDPOINT)
            .then()
            .log().ifValidationFails()
            .statusCode(CREATED.value());
    }


    @Test
    public void givenAStockIdAndAPriceDTO_whenPriceUpdatedRequested_thenOk() throws JsonProcessingException
    {

        Long stockId = 1L;

        String stockJsonRequest = objectMapper.writerWithDefaultPrettyPrinter()
            .writeValueAsString(PRICE_UPDATE_DTO);

        given()
            .contentType(JSON)
            .body(stockJsonRequest)
            .put(ENDPOINT + stockId)
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
}
