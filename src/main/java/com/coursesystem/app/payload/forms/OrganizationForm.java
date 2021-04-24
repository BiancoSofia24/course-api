package com.coursesystem.app.payload.forms;


public class OrganizationForm {
    
    private String name;
    private Long cuil;
    private String type;
    private String address;
    private String category;
    private Integer foundationYear;
    private Integer contactNumber;
    private Long agentId;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCuil() {
        return this.cuil;
    }

    public void setCuil(Long cuil) {
        this.cuil = cuil;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getFoundationYear() {
        return this.foundationYear;
    }

    public void setFoundationYear(Integer foundationYear) {
        this.foundationYear = foundationYear;
    }

    public Integer getContactNumber() {
        return this.contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getAgentId() {
        return this.agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }
    
}
