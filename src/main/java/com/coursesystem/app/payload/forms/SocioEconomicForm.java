package com.coursesystem.app.payload.forms;

public class SocioEconomicForm {
    
    private Long id;
    private Integer studying;
    private Integer working;
    private Float income;
    private Integer familyInCharge;
    private Integer dependents;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStudying() {
        return this.studying;
    }

    public void setStudying(Integer studying) {
        this.studying = studying;
    }

    public Integer getWorking() {
        return this.working;
    }

    public void setWorking(Integer working) {
        this.working = working;
    }

    public Float getIncome() {
        return this.income;
    }

    public void setIncome(Float income) {
        this.income = income;
    }

    public Integer getFamilyInCharge() {
        return this.familyInCharge;
    }

    public void setFamilyInCharge(Integer familyInCharge) {
        this.familyInCharge = familyInCharge;
    }

    public Integer getDependents() {
        return this.dependents;
    }

    public void setDependents(Integer dependents) {
        this.dependents = dependents;
    }

}
