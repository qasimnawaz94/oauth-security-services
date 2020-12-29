package com.security.services.dto.request;

import java.util.EnumMap;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.security.services.constants.RoleConstants;
import com.security.services.constants.UsersPrivilegesMap;
import com.security.services.model.validators.EmailValidator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class UserCreateDto {

    @NotNull
    @ApiModelProperty(notes = "user name")
    private String username;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @NotNull
    @EmailValidator
    private String email;

    @NotNull
    @Size(min = 6, max = 16, message = "Password Min Length should be 6 and max should be 16")
    @ApiModelProperty(notes = "User password")
    private String password;

    @ApiModelProperty(
            notes = "Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.")
    private boolean enabled;

    @Size(min = 1)
    private EnumMap<RoleConstants, List<UsersPrivilegesMap>> userRoles;

}
