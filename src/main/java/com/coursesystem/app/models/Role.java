package com.coursesystem.app.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.coursesystem.app.enums.EUserRole;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EUserRole userRole;

    public Role() {

    }

    public Role(@NotNull EUserRole userRole) {
        this.userRole = userRole;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EUserRole getUserRole() {
        return this.userRole;
    }

    public void setUserRole(EUserRole userRole) {
        this.userRole = userRole;
    }

}
