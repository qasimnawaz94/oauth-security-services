package com.security.services.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum UsersPrivilegesMap {

    ADD_USER("ADD_USER", "", ""),
    GET_USERS("GET_USERSL", "", ""),
    UPDATE_USERS("UPDATE_USERS", "", ""),
    DELETE_USERS("DELETE_USERS", "", "");

    private static final Map<String, UsersPrivilegesMap> ID_BASED_MAP = new HashMap<>();

    static {
        Stream.of(UsersPrivilegesMap.values()).forEach(o -> ID_BASED_MAP.put(o.getName(), o));
    }

    private final String name;
    private final String pageUrl;
    private final String endpoint;
}
