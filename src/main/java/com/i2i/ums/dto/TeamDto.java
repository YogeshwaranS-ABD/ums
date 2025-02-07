package com.i2i.ums.dto;

import java.util.List;

public class TeamDto {
    private String id;
    private String name;
    private List<MemberSummaryDto> members;

    public TeamDto() {}

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

    public List<MemberSummaryDto> getMembers() {
        return members;
    }

    public void setMembers(List<MemberSummaryDto> members) {
        this.members = members;
    }
}
