package com.seventhray.contracts.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Generic paginated response wrapper.
 *
 * @param <T> the type of items in the page
 */
@Data
@Builder
public class PagedResponse<T> {
    private List<T> content;
    /** 0-based page index. */
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
