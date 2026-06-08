package com.seventhray.contracts.service;

import com.seventhray.contracts.dto.ContractDetailDto;
import com.seventhray.contracts.dto.PagedResponse;
import com.seventhray.contracts.dto.WorkflowHistoryDto;
import com.seventhray.contracts.exception.ContractNotFoundException;
import com.seventhray.contracts.model.Contract;
import com.seventhray.contracts.model.WorkflowHistory;
import com.seventhray.contracts.repository.ContractRepository;
import com.seventhray.contracts.repository.WorkflowHistoryRepository;
import com.seventhray.contracts.service.impl.ContractServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContractService – unit tests")
class ContractServiceTest {

    @Mock private ContractRepository contractRepository;
    @Mock private WorkflowHistoryRepository workflowHistoryRepository;

    @InjectMocks
    private ContractServiceImpl contractService;

    private UUID contractId;
    private Contract sampleContract;

    @BeforeEach
    void setUp() {
        contractId = UUID.randomUUID();
        sampleContract = Contract.builder()
                .id(contractId)
                .title("Vendor Service Agreement")
                .description("IT support contract.")
                .status("APPROVED")
                .ownerName("Alice Smith")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    // ─── getContracts ─────────────────────────────────────────────────────────

    @Nested
    @DisplayName("getContracts")
    class GetContractsTests {

        @Test
        @DisplayName("returns paged response with correct metadata")
        void returnsPagedResponse() {
            Page<Contract> page = new PageImpl<>(
                    List.of(sampleContract), PageRequest.of(0, 10), 1);
            when(contractRepository.findByFilters(eq(""), eq(""), any())).thenReturn(page);

            PagedResponse<?> result = contractService.getContracts(null, null, 0, 10);

            assertThat(result.getTotalElements()).isEqualTo(1);
            assertThat(result.getContent()).hasSize(1);
            assertThat(result.getPage()).isZero();
        }

        @Test
        @DisplayName("passes trimmed search string to repository")
        void trimsSearchBeforeQuerying() {
            when(contractRepository.findByFilters(eq("vendor"), eq(""), any()))
                    .thenReturn(Page.empty());

            contractService.getContracts("  vendor  ", null, 0, 10);

            verify(contractRepository).findByFilters(eq("vendor"), eq(""), any(Pageable.class));
        }

        @Test
        @DisplayName("normalises status to upper-case")
        void normalisesStatusToUpperCase() {
            when(contractRepository.findByFilters(eq(""), eq("APPROVED"), any()))
                    .thenReturn(Page.empty());

            contractService.getContracts(null, "approved", 0, 10);

            verify(contractRepository).findByFilters(eq(""), eq("APPROVED"), any(Pageable.class));
        }

        @Test
        @DisplayName("treats blank search/status as empty string (no filter)")
        void treatsBlankAsEmptyString() {
            when(contractRepository.findByFilters(eq(""), eq(""), any()))
                    .thenReturn(Page.empty());

            contractService.getContracts("   ", "", 0, 10);

            verify(contractRepository).findByFilters(eq(""), eq(""), any(Pageable.class));
        }
    }

    // ─── getContractById ──────────────────────────────────────────────────────

    @Nested
    @DisplayName("getContractById")
    class GetContractByIdTests {

        @Test
        @DisplayName("returns detail DTO when contract exists")
        void returnsDetailWhenFound() {
            when(contractRepository.findById(contractId)).thenReturn(Optional.of(sampleContract));

            ContractDetailDto detail = contractService.getContractById(contractId);

            assertThat(detail.getId()).isEqualTo(contractId);
            assertThat(detail.getTitle()).isEqualTo("Vendor Service Agreement");
            assertThat(detail.getStatus()).isEqualTo("APPROVED");
        }

        @Test
        @DisplayName("throws ContractNotFoundException when contract is missing")
        void throwsNotFoundWhenMissing() {
            UUID unknownId = UUID.randomUUID();
            when(contractRepository.findById(unknownId)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> contractService.getContractById(unknownId))
                    .isInstanceOf(ContractNotFoundException.class)
                    .hasMessageContaining(unknownId.toString());
        }
    }

    // ─── getWorkflowHistory ───────────────────────────────────────────────────

    @Nested
    @DisplayName("getWorkflowHistory")
    class GetWorkflowHistoryTests {

        @Test
        @DisplayName("returns chronological history for existing contract")
        void returnsHistory() {
            WorkflowHistory entry = WorkflowHistory.builder()
                    .id(UUID.randomUUID())
                    .contractId(contractId)
                    .previousStatus(null)
                    .newStatus("DRAFT")
                    .changedBy("Alice Smith")
                    .changedAt(LocalDateTime.now())
                    .build();

            when(contractRepository.existsById(contractId)).thenReturn(true);
            when(workflowHistoryRepository.findByContractIdOrderByChangedAtAsc(contractId))
                    .thenReturn(List.of(entry));

            List<WorkflowHistoryDto> history = contractService.getWorkflowHistory(contractId);

            assertThat(history).hasSize(1);
            assertThat(history.get(0).getNewStatus()).isEqualTo("DRAFT");
            assertThat(history.get(0).getPreviousStatus()).isNull();
        }

        @Test
        @DisplayName("throws ContractNotFoundException when contract does not exist")
        void throwsNotFoundForMissingContract() {
            UUID unknownId = UUID.randomUUID();
            when(contractRepository.existsById(unknownId)).thenReturn(false);

            assertThatThrownBy(() -> contractService.getWorkflowHistory(unknownId))
                    .isInstanceOf(ContractNotFoundException.class)
                    .hasMessageContaining(unknownId.toString());

            verifyNoInteractions(workflowHistoryRepository);
        }
    }
}
