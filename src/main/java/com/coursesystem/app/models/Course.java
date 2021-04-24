package com.coursesystem.app.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.coursesystem.app.enums.ECourseStatus;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String modality;
    private Float cost;
    private Integer hours;
    private String category;
    private Integer quotas;
    private Integer scholarshipQuotas;

    @Enumerated(EnumType.STRING)
    private ECourseStatus courseStatus;

    @ManyToOne
    @JoinColumn(name = "organizations_id", nullable = false)
    private Organization org;

    public Course() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModality() {
        return this.modality;
    }

    public void setModality(String modality) {
        this.modality = modality;
    }

    public Float getCost() {
        return this.cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Integer getHours() {
        return this.hours;
    }

    public void setHours(Integer hours) {
        this.hours = hours;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuotas() {
        return this.quotas;
    }

    public void setQuotas(Integer quotas) {
        this.quotas = quotas;
    }

    public Integer getScholarshipQuotas() {
        return this.scholarshipQuotas;
    }

    public void setScholarshipQuotas(Integer scholarshipQuotas) {
        this.scholarshipQuotas = scholarshipQuotas;
    }

    public ECourseStatus getCourseStatus() {
        return this.courseStatus;
    }

    public void setCourseStatus(ECourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Organization getOrg() {
        return this.org;
    }

    public void setOrg(Organization org) {
        this.org = org;
    }

}
