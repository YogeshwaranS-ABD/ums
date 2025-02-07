package com.i2i.ums.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.i2i.ums.dto.LoginDto;
import com.i2i.ums.dto.LoginResponseDto;
import com.i2i.ums.utils.JwtUtil;

@RestController
@Slf4j
public class AuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto dto) {
        log.debug("POST Request from {} for login", dto.getUsername());
        Authentication authentication = authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername().toUpperCase(), dto.getPassword()));
        log.info("Authentication Successful for {}", dto.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        LoginResponseDto responseDto = new LoginResponseDto();
        responseDto.setToken(JwtUtil.generateToken(dto.getUsername()));
        log.info("New Token generated and sent to User successfully");
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
