package com.security.services.dto.response;

import java.util.List;
import com.security.services.constants.RoleConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleEndpointDto {

    private Long userRoleId;
    private RoleConstants userRoleName;
    private List<PrivilegeResponseDto> authorizedEndpoints;

}
