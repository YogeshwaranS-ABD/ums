package com.i2i.ums.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import org.springframework.util.ObjectUtils;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column
    private boolean isActive;

    @OneToMany(mappedBy = "team")
    private List<Member> members;

    public Team() {
        isActive = true;
    }

    public String getId() {
        return id.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public boolean isNotActive() {
        return !isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Member> getMembers() {
        if (ObjectUtils.isEmpty(members)) {
            members = new ArrayList<>();
        }
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public int getMemberCount() {
        return ObjectUtils.isEmpty(members) ? 0 : members.size();
    }
}
