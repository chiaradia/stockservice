package com.payconiq.stockservice.domainobject;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class Price implements Serializable
{
    BigDecimal value;
    String currency;
}
