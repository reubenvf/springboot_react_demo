package com.example.demo.dto;

import java.util.List;

// ReubenVF: This generic paging response helps API clients understand page metadata alongside the actual list items.
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean last
) {
}
