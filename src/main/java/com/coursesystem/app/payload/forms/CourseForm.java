package com.coursesystem.app.payload.forms;

public class CourseForm {

    private String name;
    private String description;
    private String modality;
    private Float cost;
    private Integer hours;
    private String category;
    private Integer quotas;
    private Integer scholarshipQuotas;
    private Long orgId;

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

    public Long getOrgId() {
        return this.orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

}
