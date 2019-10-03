package com.payconiq.stockservice.domainobject;

import com.payconiq.stockservice.domainvalue.StockStatus;
import java.io.Serializable;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Stock implements Serializable
{
    Long id;
    String name;
    Price currentPrice;
    Price lastPrice;
    OffsetDateTime dateLastUpdated;
    OffsetDateTime dateCreated;
    StockStatus stockStatus;
}
