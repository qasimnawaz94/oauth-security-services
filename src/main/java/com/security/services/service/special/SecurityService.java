package com.security.services.service.special;

import java.util.Collection;
import java.util.Objects;
import org.bouncycastle.util.Arrays;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.security.services.constants.SuperAdminPrivileges;
import com.security.services.constants.UsersPrivilegesMap;


@Component("securityService")
public class SecurityService {

    public boolean hasPermission(SuperAdminPrivileges adminPrivilege, UsersPrivilegesMap... permissions) {
        // loop over each submitted role and validate the user has at least one
        Collection<? extends GrantedAuthority> userAuthorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (Objects.nonNull(adminPrivilege) &&
            userAuthorities.contains(new SimpleGrantedAuthority(adminPrivilege.name()))) {
            return true;
        }
        if (!Arrays.isNullOrContainsNull(permissions)) {
            for (UsersPrivilegesMap permission : permissions) {
                if (userAuthorities.contains(new SimpleGrantedAuthority(permission.getName()))) {
                    return true;
                }
            }
        }
        // no matching permission found
        return false;
    }


    public boolean hasPermission(SuperAdminPrivileges... adminPrivilege) {
        // loop over each submitted role and validate the user has at least one
        Collection<? extends GrantedAuthority> userAuthorities =
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();


        if (!Arrays.isNullOrContainsNull(adminPrivilege)) {
            for (SuperAdminPrivileges permission : adminPrivilege) {
                if (userAuthorities.contains(new SimpleGrantedAuthority(permission.name()))) {
                    return true;
                }
            }
        }

        // no matching permission found
        return false;
    }
}
