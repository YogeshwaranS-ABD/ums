package com.i2i.ums.dto;

import java.util.List;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.i2i.ums.annotations.CustomCheckGroups;
import com.i2i.ums.annotations.ValidCredentials;
import com.i2i.ums.mapper.AddressConverter;
import com.i2i.ums.mapper.ContactsConverter;
import com.i2i.ums.mapper.RoleConverter;

@ValidCredentials
public class MemberDto {
    private String id;

    @NotBlank(
            groups = CustomCheckGroups.class,
            message = "Name of the member Should not be Blank or Empty")
    @CsvBindByName(column = "name")
    private String name;

    @CsvBindByName(column = "username")
    private String username;

    @CsvBindByName(column = "password")
    private String password;

    @NotNull(groups = CustomCheckGroups.class,
            message = "New Member should be part of a team. TeamName is null")
    @CsvBindByName(column = "teamName")
    private String teamName;

    @CsvCustomBindByName(column = "contacts", converter = ContactsConverter.class)
    private List<@Valid ContactDto> contacts;

    @CsvCustomBindByName(column = "roles", converter = RoleConverter.class)
    private List<String> roles;

    @CsvCustomBindByName(column = "address", converter = AddressConverter.class)
    private AddressDto address;

    public MemberDto() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<ContactDto> getContacts() {
        return contacts;
    }

    public void setContacts(List<ContactDto> contacts) {
        this.contacts = contacts;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
