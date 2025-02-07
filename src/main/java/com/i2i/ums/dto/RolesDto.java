package com.i2i.ums.dto;

import java.util.List;

public class RolesDto {
    private String action;
    private List<String> roles;

    public RolesDto() {}

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}