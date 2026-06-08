package com.seventhray.contracts.controller;

import com.seventhray.contracts.dto.*;
import com.seventhray.contracts.exception.ContractNotFoundException;
import com.seventhray.contracts.exception.GlobalExceptionHandler;
import com.seventhray.contracts.service.ContractService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ContractController.class)
@Import(GlobalExceptionHandler.class)
@DisplayName("ContractController – API tests")
class ContractControllerTest {

    @Autowired private MockMvc mockMvc;
    @MockBean  private ContractService contractService;

    private static final UUID SAMPLE_ID = UUID.fromString("9b1deb4d-3b7d-4bad-9bdd-2b0d7b3dcb6d");

    // ─── GET /api/contracts ────────────────────────────────────────────────────

    @Nested
    @DisplayName("GET /api/contracts")
    class ListContractsTests {

        @Test
        @DisplayName("returns 200 with paginated content")
        void returns200WithContent() throws Exception {
            ContractSummaryDto summary = buildSummary(SAMPLE_ID, "Vendor Agreement", "Alice", "APPROVED");
            when(contractService.getContracts(any(), any(), anyInt(), anyInt()))
                    .thenReturn(buildPage(List.of(summary)));

            mockMvc.perform(get("/api/contracts").contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.totalElements").value(1))
                    .andExpect(jsonPath("$.content[0].title").value("Vendor Agreement"))
                    .andExpect(jsonPath("$.content[0].status").value("APPROVED"));
        }

        @Test
        @DisplayName("returns 200 with empty content when no contracts match")
        void returns200WhenEmpty() throws Exception {
            when(contractService.getContracts(any(), any(), anyInt(), anyInt()))
                    .thenReturn(buildPage(List.of()));

            mockMvc.perform(get("/api/contracts?status=REVIEW"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isEmpty())
                    .andExpect(jsonPath("$.totalElements").value(0));
        }

        @Test
        @DisplayName("returns 400 when page is negative")
        void returns400ForNegativePage() throws Exception {
            mockMvc.perform(get("/api/contracts?page=-1"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("returns 400 when size exceeds 100")
        void returns400ForOversizedPage() throws Exception {
            mockMvc.perform(get("/api/contracts?size=200"))
                    .andExpect(status().isBadRequest());
        }
    }

    // ─── GET /api/contracts/{id} ───────────────────────────────────────────────

    @Nested
    @DisplayName("GET /api/contracts/{id}")
    class GetContractTests {

        @Test
        @DisplayName("returns 200 with full contract detail when found")
        void returns200WhenFound() throws Exception {
            ContractDetailDto detail = ContractDetailDto.builder()
                    .id(SAMPLE_ID)
                    .title("Vendor Agreement")
                    .description("Annual IT support contract.")
                    .status("APPROVED")
                    .ownerName("Alice Smith")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            when(contractService.getContractById(SAMPLE_ID)).thenReturn(detail);

            mockMvc.perform(get("/api/contracts/" + SAMPLE_ID))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(SAMPLE_ID.toString()))
                    .andExpect(jsonPath("$.title").value("Vendor Agreement"))
                    .andExpect(jsonPath("$.description").value("Annual IT support contract."));
        }

        @Test
        @DisplayName("returns 404 with error body when contract not found")
        void returns404WhenNotFound() throws Exception {
            when(contractService.getContractById(SAMPLE_ID))
                    .thenThrow(new ContractNotFoundException(SAMPLE_ID));

            mockMvc.perform(get("/api/contracts/" + SAMPLE_ID))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.error").value("Not Found"))
                    .andExpect(jsonPath("$.message").value("Contract not found with id: " + SAMPLE_ID))
                    .andExpect(jsonPath("$.timestamp").exists());
        }

        @Test
        @DisplayName("returns 400 when id is not a valid UUID")
        void returns400ForInvalidUuid() throws Exception {
            mockMvc.perform(get("/api/contracts/not-a-uuid"))
                    .andExpect(status().isBadRequest());
        }
    }

    // ─── GET /api/contracts/{id}/history ──────────────────────────────────────

    @Nested
    @DisplayName("GET /api/contracts/{id}/history")
    class GetHistoryTests {

        @Test
        @DisplayName("returns 200 with workflow history list")
        void returns200WithHistory() throws Exception {
            WorkflowHistoryDto entry = WorkflowHistoryDto.builder()
                    .id(UUID.randomUUID())
                    .contractId(SAMPLE_ID)
                    .previousStatus(null)
                    .newStatus("DRAFT")
                    .changedBy("Alice Smith")
                    .changedAt(LocalDateTime.now())
                    .build();

            when(contractService.getWorkflowHistory(SAMPLE_ID)).thenReturn(List.of(entry));

            mockMvc.perform(get("/api/contracts/" + SAMPLE_ID + "/history"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].newStatus").value("DRAFT"))
                    .andExpect(jsonPath("$[0].changedBy").value("Alice Smith"));
        }

        @Test
        @DisplayName("returns 404 when contract does not exist")
        void returns404ForMissingContract() throws Exception {
            when(contractService.getWorkflowHistory(SAMPLE_ID))
                    .thenThrow(new ContractNotFoundException(SAMPLE_ID));

            mockMvc.perform(get("/api/contracts/" + SAMPLE_ID + "/history"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404));
        }
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private ContractSummaryDto buildSummary(UUID id, String title, String owner, String status) {
        return ContractSummaryDto.builder()
                .id(id).title(title).ownerName(owner).status(status)
                .createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now())
                .build();
    }

    private PagedResponse<ContractSummaryDto> buildPage(List<ContractSummaryDto> content) {
        return PagedResponse.<ContractSummaryDto>builder()
                .content(content)
                .page(0).size(10)
                .totalElements(content.size())
                .totalPages(content.isEmpty() ? 0 : 1)
                .last(true)
                .build();
    }
}
