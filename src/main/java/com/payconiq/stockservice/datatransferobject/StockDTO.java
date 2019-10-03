package com.payconiq.stockservice.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.payconiq.stockservice.domainobject.Price;
import com.payconiq.stockservice.domainvalue.StockStatus;
import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder(toBuilder = true)
public class StockDTO
{
    private final Long id;
    private final String name;
    private final Price currentPrice;
    private final Price lastPrice;
    private final OffsetDateTime dateLastUpdated;
    private final OffsetDateTime dateCreated;
    private final StockStatus stockStatus;
}
