package com.i2i.ums.service;

import com.i2i.ums.dto.IdNameDto;
import com.i2i.ums.dto.RoleDto;
import com.i2i.ums.model.Role;

public interface RoleService {
    IdNameDto createRole(RoleDto dto);
    Role getRoleByName(String name);
}
