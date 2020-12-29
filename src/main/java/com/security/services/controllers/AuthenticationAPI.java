package com.security.services.controllers;

import java.security.Principal;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.security.services.dto.response.UserResponseDto;
import com.security.services.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/authorized")
@Api(tags = "Authentication API", description = "Authenticate user using authorization token.")
public class AuthenticationAPI {

    private static final Logger LOGGER = Logger.getLogger(AuthenticationAPI.class);

    @Autowired
    @Qualifier("userDetailsService")
    private UserDetailsService userDetailsService;


    @Autowired
    private UserRepository userRepository;


    @ApiOperation(value = "Authenticated User Login", response = UserResponseDto.class)
    @PostMapping(value = "/login")
    public ResponseEntity<UserResponseDto> login(Principal principal) {
        String username = principal.getName();
        return new ResponseEntity<UserResponseDto>(UserResponseDto.mapUser(userRepository.findOneByUsername(username)),
                HttpStatus.OK);
    }

}
