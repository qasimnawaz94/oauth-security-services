package com.security.services.dto.common;

import java.util.List;
import org.springframework.data.domain.Page;


public class PagingResponse<T> {

    private int totalPages;
    private long totalElements;
    private int pageNumber;
    private int pageSize;
    private List<T> content;


   public static <T> PagingResponse<T> of(Page<T> page) {
        return new PagingResponse<T>(page.getTotalPages(), page.getTotalElements(), page.getNumber(), page.getSize(),
                page.getContent());
    }


    public PagingResponse(int totalPages, long totalElements, int pageNumber, int pageSize, List<T> content) {
        super();
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.content = content;
    }



    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }


}
