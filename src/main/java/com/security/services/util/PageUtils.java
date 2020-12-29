package com.security.services.util;

import java.util.Objects;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.security.services.dto.common.PagingRequest;

/**
 * 
 * @author qasim
 *
 */
public class PageUtils {

    public static PageRequest of(int page, int size, Sort sort) {
        return PageRequest.of(page, size, sort);
    }

    public static Sort sort(PagingRequest request) {
        return Sort.by(request.isDescending() ? Sort.Direction.DESC : Sort.Direction.ASC, "id");
    }

    public static Pageable getPage(PagingRequest request) {
        if (Objects.isNull(request)) {
            return Pageable.unpaged();
        }

        if (request.getPageNumber() < 0 || request.getPageSize() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Page number or size");
        }

        return of(request.getPageNumber(), request.getPageSize(), sort(request));
    }
}
