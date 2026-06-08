package com.seventhray.contracts.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/** Full contract payload returned by GET /api/contracts/{id}. */
@Data
@Builder
public class ContractDetailDto {
    private UUID id;
    private String title;
    private String description;
    private String status;
    private String ownerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
