package com.i2i.ums.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.i2i.ums.dto.IdNameDto;
import com.i2i.ums.dto.RoleDto;
import com.i2i.ums.exception.RoleNotFoundException;
import com.i2i.ums.model.Role;
import com.i2i.ums.repository.RoleRepository;
import com.i2i.ums.service.impl.RoleServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @InjectMocks
    private RoleServiceImpl roleService;

    @Mock
    private RoleRepository roleRepository;

    private Role role;
    private RoleDto roleDto;

    @BeforeEach
    public void setup() {
        role = new Role();
        role.setId("some_uuid");
        role.setName("TEST_ROLE");
        role.setDescription("test-description");

        roleDto = new RoleDto();
        roleDto.setName("TEST_ROLE");
        roleDto.setDescription("test-description");
    }

    @Test
    public void testCreateRole() {
        when(roleRepository.save(any())).thenReturn(role);
        IdNameDto dto = roleService.createRole(roleDto);
        assertThat(dto.getId()).isNotNull();
        assertEquals(roleDto.getName(), dto.getName());
    }

    @Test
    public void testGetRoleByName_validName() {
        when(roleRepository.findByNameIgnoreCase("TEST_ROLE")).thenReturn(role);
        Role role = roleService.getRoleByName("test_role");
        assertThat(role).isNotNull();
        assertEquals("TEST_ROLE", role.getName());
        assertThat(role.getId()).isNotNull();
    }

    @Test
    public void testGetRoleByName_invalidName() {
        when(roleRepository.findByNameIgnoreCase("TEST_ROLE")).thenReturn(null);
        assertThrows(RoleNotFoundException.class, () -> roleService.getRoleByName("test_role"));
    }

//    @Test
//    public void testDeleteRole() {
//        when(roleRepository.findByNameIgnoreCase("TEST_ROLE")).thenReturn(role);
//        assertDoesNotThrow(() -> roleService.deleteRoleByName("test_role"));
//    }
}
