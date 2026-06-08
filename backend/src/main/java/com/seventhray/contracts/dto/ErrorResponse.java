package com.seventhray.contracts.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/** Uniform error envelope returned for all 4xx / 5xx responses. */
@Data
@Builder
public class ErrorResponse {
    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;
}
