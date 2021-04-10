package com.coursesystem.app.models;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "agents")
public class Agent {
    
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    private User user;

    @NotNull
    private String name;

    @NotNull
    private String lastname;
    
    @NotNull
    private String documentType;
    
    @NotNull
    private Integer documentNumber;

    public Agent() {
    }

    public Agent(@NotNull String name, @NotNull String lastname, @NotNull String documentType, @NotNull Integer documentNumber) {
        this.name = name;
        this.lastname = lastname;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
    }


    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDocumentType() {
        return this.documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public Integer getDocumentNumber() {
        return this.documentNumber;
    }

    public void setDocumentNumber(Integer documentNumber) {
        this.documentNumber = documentNumber;
    }

}
