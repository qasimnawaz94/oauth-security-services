package com.security.services.service.special;

import java.io.Serializable;
import org.apache.log4j.Logger;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;


@Component
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private static final Logger LOGGER = Logger.getLogger(CustomPermissionEvaluator.class);

    public CustomPermissionEvaluator() {}

    @Override
    public boolean hasPermission(Authentication arg0, Object arg1, Object arg2) {


        if (arg0 == null || !arg0.isAuthenticated()) {
            LOGGER.debug(String.format("Not Allowed %s ", arg0));
            return false;
        }
        else {
            LOGGER.debug(String.format("Checking Permission  for %s ", arg0.getName()));
            LOGGER.debug(String.format("User Permissions are %s ", arg0.getAuthorities()));

            for (GrantedAuthority authority : arg0.getAuthorities()) {
                if (authority.getAuthority().equals(arg2)) {
                    LOGGER.debug(String.format("User Allowed for %s ", arg2));
                    return true;
                }
            }
            LOGGER.debug(String.format("User NOT Allowed for %s ", arg2));
            return false;
        }
    }


    @Override
    public boolean hasPermission(Authentication arg0, Serializable arg1, String arg2, Object arg3) {
        throw new RuntimeException("Id-based permission evaluation not currently supported.");
    }

}
