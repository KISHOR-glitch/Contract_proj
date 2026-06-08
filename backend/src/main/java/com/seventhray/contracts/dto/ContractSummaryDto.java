package com.seventhray.contracts.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/** Lightweight projection used in the paginated contracts list. */
@Data
@Builder
public class ContractSummaryDto {
    private UUID id;
    private String title;
    private String ownerName;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
