package com.security.services.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.security.services.constants.RoleConstants;
import com.security.services.model.User;
import com.security.services.model.UserRole;
import com.security.services.model.jsonSerializers.LocalDateTimeDeserializer;
import com.security.services.model.jsonSerializers.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    private String userName;
    private String fullName;
    private List<RoleEndpointDto> rolesDetails;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    public LocalDateTime createdOn;


    public static UserResponseDto mapUser(User user) {
        List<UserRole> userRoles = user.getUserRoles();

        List<RoleEndpointDto> details = new ArrayList<>();
        userRoles.forEach(oneUserRole -> {

            List<PrivilegeResponseDto> authorizedEndpoints = new ArrayList<>();

            oneUserRole.getRolePrivileges().forEach(rolePriv -> {
                PrivilegeResponseDto one = PrivilegeResponseDto
                        .builder()
                        .id(rolePriv.getId())
                        .endpoint(rolePriv.getPrivilege().getEndpoint())
                        .name(rolePriv.getPrivilege().getName())
                        .pageUrl(rolePriv.getPrivilege().getPageUrl())
                        .build();
                authorizedEndpoints.add(one);
            });

            RoleEndpointDto one = RoleEndpointDto
                    .builder()
                    .userRoleId(oneUserRole.getId())
                    .userRoleName(oneUserRole.getRole().getName())
                    .authorizedEndpoints(authorizedEndpoints)
                    .build();
            details.add(one);
        });

        return UserResponseDto
                .builder()
                .fullName(user.getFullName())
                .userName(user.getUsername())
                .rolesDetails(details)
                .build();


    }

}
