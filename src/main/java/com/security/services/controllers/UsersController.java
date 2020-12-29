package com.security.services.controllers;

import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.security.services.dto.request.UserCreateDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/users")
@Api(tags = "Users Controller", description = "User Operations")
public class UsersController {

    private static final Logger LOGGER = Logger.getLogger(UsersController.class);


    @ApiOperation(value = "Create User Endpoint")
    @PostMapping(value = "/createUser")
    @PreAuthorize("hasPermission(#createUserDto, 'WRITE_PRIVILEGE') || hasPermission(#createUserDto, 'ADD_USER')  ")
    public void createUser(@Valid @RequestBody UserCreateDto createUserDto) {

        LOGGER.info("user has access");
        LOGGER.info(createUserDto.toString());

    }
}
