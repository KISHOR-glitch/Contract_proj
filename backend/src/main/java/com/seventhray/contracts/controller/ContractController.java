package com.seventhray.contracts.controller;

import com.seventhray.contracts.dto.ContractDetailDto;
import com.seventhray.contracts.dto.ContractSummaryDto;
import com.seventhray.contracts.dto.PagedResponse;
import com.seventhray.contracts.dto.WorkflowHistoryDto;
import com.seventhray.contracts.service.ContractService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller exposing the Contracts API.
 *
 * <pre>
 * GET /api/contracts              – paginated list with optional search & filter
 * GET /api/contracts/{id}         – contract detail
 * GET /api/contracts/{id}/history – workflow history
 * </pre>
 */
@RestController
@RequestMapping("/api/contracts")
@RequiredArgsConstructor
@Validated
public class ContractController {

    private final ContractService contractService;

    /**
     * List contracts with optional pagination, search and status filter.
     *
     * <p>Examples:</p>
     * <ul>
     *   <li>{@code GET /api/contracts?page=0&size=10}</li>
     *   <li>{@code GET /api/contracts?status=REVIEW}</li>
     *   <li>{@code GET /api/contracts?search=vendor}</li>
     *   <li>{@code GET /api/contracts?search=alice&status=APPROVED&page=0&size=5}</li>
     * </ul>
     *
     * @param search optional free-text search (matches title or owner, case-insensitive)
     * @param status optional exact status filter (e.g. DRAFT, REVIEW, APPROVED, REJECTED, TERMINATED)
     * @param page   0-based page number (default 0)
     * @param size   number of items per page, 1–100 (default 10)
     */
    @GetMapping
    public ResponseEntity<PagedResponse<ContractSummaryDto>> listContracts(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size
    ) {
        return ResponseEntity.ok(contractService.getContracts(search, status, page, size));
    }

    /**
     * Retrieve full details for a single contract.
     *
     * @param id contract UUID
     * @return 200 with {@link ContractDetailDto}, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractDetailDto> getContract(@PathVariable UUID id) {
        return ResponseEntity.ok(contractService.getContractById(id));
    }

    /**
     * Retrieve the chronological workflow history for a contract.
     *
     * @param id contract UUID
     * @return 200 with list of {@link WorkflowHistoryDto}, or 404 if contract not found
     */
    @GetMapping("/{id}/history")
    public ResponseEntity<List<WorkflowHistoryDto>> getHistory(@PathVariable UUID id) {
        return ResponseEntity.ok(contractService.getWorkflowHistory(id));
    }
}
