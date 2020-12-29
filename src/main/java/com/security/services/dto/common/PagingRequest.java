package com.security.services.dto.common;


public class PagingRequest {

    public static PagingRequest defaultFilter() {
        PagingRequest request = new PagingRequest();
        request.setPageNumber(0);
        request.setPageSize(10);
        return request;
    }

    private int pageNumber;
    private int pageSize;
    private String keyword;
    private boolean descending;

    public PagingRequest() {
        this.setPageNumber(0);
        this.setPageSize(10);
        this.setDescending(true);
    }

    public String getKeyword() {
        return keyword;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isDescending() {
        return descending;
    }

    public void setDescending(boolean descending) {
        this.descending = descending;
    }


}
