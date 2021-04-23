package com.coursesystem.app.payload.forms;

import com.coursesystem.app.enums.ECourseStatus;

public class CourseForm {

    private Long id;
    private String name;
    private String description;
    private String modality;
    private Float cost;
    private Integer hours;
    private Integer quotas;
    private Integer scholarshipQuotas;
    private ECourseStatus courseStatus;
    private Long orgId;

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

    public Long getOrgID() {
        return this.orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

}
