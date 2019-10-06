[![JDK](https://img.shields.io/badge/OpenJDK-8-orange)](https://img.shields.io/badge/OpenJDK-8-orange)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=com.payconiq%3Astockservice&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.payconiq%3Astockservice)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=com.payconiq%3Astockservice&metric=coverage)](https://sonarcloud.io/dashboard?id=com.payconiq%3Astockservice)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=com.payconiq%3Astockservice&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=com.payconiq%3Astockservice)
[![Build Status](https://travis-ci.org/chiaradia/stockservice.svg?branch=master)](https://travis-ci.org/chiaradia/stockservice)

# About this project

This application aims to expose a RESTful API to perform CRUD operations for stock management.

## Stack
- Docker
- Java 8
- Spring Boot
- Maven
- JUnit 4 and Mockito for unit testing
- Travis CI
- Postman

## Running the application

### As a regular Spring Boot application 
    $ git clone https://github.com/chiaradia/stockservice.git
    $ cd stockservice
    $ ./mvnw -f stockservice/pom.xml spring-boot:run
    $ curl http://localhost:8080/api/stocks

### With docker-compose (cloning the repository)  
    
    $ git clone https://github.com/chiaradia/stockservice.git
    $ cd stockservice
    $ docker-compose up
    $ curl http://localhost:8080/api/stocks
    
## Endpoints
| Name 	| Path 	| Method 	| Content-Type 	| Description 	|
|------	|------	|--------	|--------------	|-------------	|
|   Get all Stocks   	|   /api/stocks   	|     GET   	| application/json              	| Return all stocks stored            	|
|   Get Stock by Id   	|   /api/stocks/{stockId}   	|      GET  	| application/json             	| Return a single Stock based on its ID            	|
|   Create Stock   	|   /api/stocks   	|      POST  	| application/json             	| Create a new Stock            	|
|   Update Price   	|   /api/stocks/{stockId}   	|      PUT  	| application/json             	|     Update the current price of an existing Stock        	|
|   Delete Stock   	|   /api/stocks/{stockId}   	|      DELETE  	| application/json             	|   Delete a Stock based on its ID          	|

### Swagger 

The Swagger documentation is available on http://localhost:8080/swagger-ui.html#/stock-controller

### Actuator (metrics) 

This project is shipped with Actuator enable. The default metrics (Prometheus) are exposed on the default endpoint http://localhost:8080/actuator/prometheus.

Note that is possible to create custom ones, since Micrometer dependency has been added to the POM file.

## Testing the application
    $ cd stockservice
    $ ./mvnw test

Furthermore, it's highly recommended to take a look at the Postman collection. It's possible to test the entire API with it. You just need to download [Postman](https://www.getpostman.com/apps) and import this [collection](https://github.com/chiaradia/stockservice/postman/stockservice.postman_collection.json).

## Improvements

 - Write Integration Tests
 - Add Kubernetes support for Continuous Deployment with Travis CI