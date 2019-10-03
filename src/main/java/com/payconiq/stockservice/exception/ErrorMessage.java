package com.payconiq.stockservice.exception;

import java.time.OffsetDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorMessage
{
    private String message;
    private OffsetDateTime timeStamp;
}
