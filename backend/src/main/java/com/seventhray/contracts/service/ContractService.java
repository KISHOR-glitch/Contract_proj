package com.seventhray.contracts.service;

import com.seventhray.contracts.dto.ContractDetailDto;
import com.seventhray.contracts.dto.ContractSummaryDto;
import com.seventhray.contracts.dto.PagedResponse;
import com.seventhray.contracts.dto.WorkflowHistoryDto;

import java.util.List;
import java.util.UUID;

/**
 * Business logic interface for the Contracts module.
 * Keeping the interface separate from the implementation enables easy mocking
 * in tests and supports future substitution (e.g., caching decorator).
 */
public interface ContractService {

    /**
     * Returns a paginated, optionally filtered list of contracts.
     *
     * @param search free-text search applied to title and owner name (nullable / blank = no filter)
     * @param status exact status filter, e.g. "APPROVED" (nullable / blank = no filter)
     * @param page   0-based page index
     * @param size   page size (1–100)
     */
    PagedResponse<ContractSummaryDto> getContracts(String search, String status, int page, int size);

    /**
     * Returns complete details for a single contract.
     *
     * @throws com.seventhray.contracts.exception.ContractNotFoundException if not found
     */
    ContractDetailDto getContractById(UUID id);

    /**
     * Returns the full workflow history for a contract, ordered chronologically.
     *
     * @throws com.seventhray.contracts.exception.ContractNotFoundException if contract not found
     */
    List<WorkflowHistoryDto> getWorkflowHistory(UUID contractId);
}
