package com.coursesystem.app.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.coursesystem.app.enums.EStatus;

@Entity
@Table(name = "students")
public class Student {

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
    private Date birthday;
    @NotNull
    private String gender;
    @NotNull
    private String location;

    private Boolean studying;
    private Boolean working;
    private Float income;
    private Boolean familyInCharge;
    private Integer dependents;

    @Enumerated(EnumType.STRING)
    private EStatus scholarshipStatus;

    public Student() {
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

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isStudying() {
        return this.studying;
    }

    public Boolean getStudying() {
        return this.studying;
    }

    public void setStudying(Boolean studying) {
        this.studying = studying;
    }

    public Boolean isWorking() {
        return this.working;
    }

    public Boolean getWorking() {
        return this.working;
    }

    public void setWorking(Boolean working) {
        this.working = working;
    }

    public Float getIncome() {
        return this.income;
    }

    public void setIncome(Float income) {
        this.income = income;
    }

    public Boolean isFamilyInCharge() {
        return this.familyInCharge;
    }

    public Boolean getFamilyInCharge() {
        return this.familyInCharge;
    }

    public void setFamilyInCharge(Boolean familyInCharge) {
        this.familyInCharge = familyInCharge;
    }

    public Integer getDependents() {
        return this.dependents;
    }

    public void setDependents(Integer dependents) {
        this.dependents = dependents;
    }

    public EStatus getScholarshipStatus() {
        return this.scholarshipStatus;
    }

    public void setScholarshipStatus(EStatus scholarshipStatus) {
        this.scholarshipStatus = scholarshipStatus;
    }

}
