package com.i2i.ums.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.i2i.ums.dto.IdNameDto;
import com.i2i.ums.dto.RoleDto;
import com.i2i.ums.service.RoleService;
import com.i2i.ums.service.impl.RoleServiceImpl;

@RestController
@RequestMapping(value = "/roles", produces = "application/json")
@Slf4j
public class RoleController {

    private static final Logger log = LoggerFactory.getLogger(RoleController.class);
    private final RoleService roleService;

    @Autowired
    public RoleController(RoleServiceImpl roleService) {
        this.roleService = roleService;
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<IdNameDto> createRole(@RequestBody RoleDto dto) {
        log.debug("POST Request to create a Role: {} - {}", dto.getName(), dto.getDescription());
        return new ResponseEntity<>(roleService.createRole(dto), HttpStatus.CREATED);
    }
}
