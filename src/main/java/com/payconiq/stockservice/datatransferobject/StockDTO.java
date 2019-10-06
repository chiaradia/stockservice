package com.payconiq.stockservice.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String name;
    @NotNull
    private PriceDTO currentPrice;
    private OffsetDateTime dateLastUpdated;
    private OffsetDateTime dateCreated;
}
