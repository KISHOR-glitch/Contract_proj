package com.seventhray.contracts.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/** Represents a single workflow status transition for a contract. */
@Data
@Builder
public class WorkflowHistoryDto {
    private UUID id;
    private UUID contractId;
    /** Null if this entry represents the initial creation. */
    private String previousStatus;
    private String newStatus;
    private String changedBy;
    private LocalDateTime changedAt;
}
