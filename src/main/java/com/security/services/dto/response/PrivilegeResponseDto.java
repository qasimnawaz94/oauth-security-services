package com.security.services.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PrivilegeResponseDto {

    private String name;
    private String endpoint;
    private String pageUrl;
    private Long id;
}
