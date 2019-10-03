package com.payconiq.stockservice.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.payconiq.stockservice.domainobject.Price;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class StockDTO
{
    private Long id;
    private String name;
    private Price currentPrice;
    private Price lastPrice;
    private OffsetDateTime dateLastUpdated;
    private OffsetDateTime dateCreated;
}
