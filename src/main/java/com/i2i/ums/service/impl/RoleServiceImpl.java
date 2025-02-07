package com.i2i.ums.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.i2i.ums.dto.IdNameDto;
import com.i2i.ums.dto.RoleDto;
import com.i2i.ums.exception.RoleNotFoundException;
import com.i2i.ums.mapper.Mapper;
import com.i2i.ums.model.Role;
import com.i2i.ums.repository.RoleRepository;
import com.i2i.ums.service.RoleService;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private static final Logger log = LoggerFactory.getLogger(RoleServiceImpl.class);
    private final RoleRepository roleRepository;
    private final Mapper mapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository,
                           Mapper mapper) {
        this.roleRepository = roleRepository;
        this.mapper = mapper;
    }

    @Override
    public IdNameDto createRole(RoleDto dto) {
        Role role = mapper.map(dto, Role.class);
        role = roleRepository.save(role);
        log.info("New Role Created Successfully");
        return new IdNameDto(role.getId(), role.getName());
    }

    @Override
    public Role getRoleByName(String name) {
        Role role = roleRepository.findByNameIgnoreCase(name);
        if (ObjectUtils.isEmpty(role)) {
            log.error("Role: {}, Does not exist", name);
            throw new RoleNotFoundException("No Role matched with the given value");
        }
        log.info("Role: {} Fetched Successfully", name);
        return role;
    }
}
