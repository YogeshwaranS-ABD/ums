package com.i2i.ums.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import org.springframework.util.ObjectUtils;

import com.i2i.ums.utils.Util;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String name;

    @Column(unique = true)
    private String username;

    @Column(unique = true)
    private String password;

    @Column
    private boolean isDeleted;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "member")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "member")
    private List<Contact> contacts;

    @ManyToOne
    @JoinColumn(name = "teamId")
    private Team team;

    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private Set<Role> roles;

    public Member() {
        isDeleted = false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toUpperCase();
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
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Contact> getContacts() {
        if (ObjectUtils.isEmpty(contacts)) {
            contacts = new ArrayList<>();
        }
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Role> getRoles() {
        if (ObjectUtils.isEmpty(roles)) {
            roles = new HashSet<>();
        }
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void addRole(Role role) {
        if (ObjectUtils.isEmpty(roles)) {
            roles = new HashSet<>();
        }
        role.getMembers().add(this);
        roles.add(role);
    }

    public void removeRole(Role role) {
        role.getMembers().remove(this);
        roles.remove(role);
    }

    public void addContact(Contact contact) {
        contact.setMember(this);
        this.contacts.add(contact);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @PrePersist
    public void onCreate() {
        this.password = Util.getEncoder().encode(this.password);
    }
}
