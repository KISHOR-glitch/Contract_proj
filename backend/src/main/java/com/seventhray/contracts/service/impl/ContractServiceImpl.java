package com.seventhray.contracts.service.impl;

import com.seventhray.contracts.dto.*;
import com.seventhray.contracts.exception.ContractNotFoundException;
import com.seventhray.contracts.model.Contract;
import com.seventhray.contracts.model.WorkflowHistory;
import com.seventhray.contracts.repository.ContractRepository;
import com.seventhray.contracts.repository.WorkflowHistoryRepository;
import com.seventhray.contracts.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link ContractService}.
 * All public methods are read-only transactions; no writes occur in this module.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContractServiceImpl implements ContractService {

    private final ContractRepository contractRepository;
    private final WorkflowHistoryRepository workflowHistoryRepository;

    // ─── Public API ───────────────────────────────────────────────────────────

    @Override
    public PagedResponse<ContractSummaryDto> getContracts(String search, String status, int page, int size) {
        // Normalise inputs: blank strings treated as "no filter" (empty string instead of null for Postgres compatibility)
        String searchParam = (search == null || search.isBlank()) ? "" : search.trim();
        String statusParam = (status == null || status.isBlank()) ? "" : status.trim().toUpperCase();

        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Contract> resultPage = contractRepository.findByFilters(searchParam, statusParam, pageRequest);

        List<ContractSummaryDto> content = resultPage.getContent().stream()
                .map(this::toSummaryDto)
                .collect(Collectors.toList());

        return PagedResponse.<ContractSummaryDto>builder()
                .content(content)
                .page(resultPage.getNumber())
                .size(resultPage.getSize())
                .totalElements(resultPage.getTotalElements())
                .totalPages(resultPage.getTotalPages())
                .last(resultPage.isLast())
                .build();
    }

    @Override
    public ContractDetailDto getContractById(UUID id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new ContractNotFoundException(id));
        return toDetailDto(contract);
    }

    @Override
    public List<WorkflowHistoryDto> getWorkflowHistory(UUID contractId) {
        // Guard: ensure the contract exists before querying history
        if (!contractRepository.existsById(contractId)) {
            throw new ContractNotFoundException(contractId);
        }
        return workflowHistoryRepository
                .findByContractIdOrderByChangedAtAsc(contractId)
                .stream()
                .map(this::toHistoryDto)
                .collect(Collectors.toList());
    }

    // ─── Private Mappers ──────────────────────────────────────────────────────

    private ContractSummaryDto toSummaryDto(Contract c) {
        return ContractSummaryDto.builder()
                .id(c.getId())
                .title(c.getTitle())
                .ownerName(c.getOwnerName())
                .status(c.getStatus())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }

    private ContractDetailDto toDetailDto(Contract c) {
        return ContractDetailDto.builder()
                .id(c.getId())
                .title(c.getTitle())
                .description(c.getDescription())
                .status(c.getStatus())
                .ownerName(c.getOwnerName())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }

    private WorkflowHistoryDto toHistoryDto(WorkflowHistory wh) {
        return WorkflowHistoryDto.builder()
                .id(wh.getId())
                .contractId(wh.getContractId())
                .previousStatus(wh.getPreviousStatus())
                .newStatus(wh.getNewStatus())
                .changedBy(wh.getChangedBy())
                .changedAt(wh.getChangedAt())
                .build();
    }
}
