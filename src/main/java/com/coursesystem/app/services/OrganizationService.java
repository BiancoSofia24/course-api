package com.coursesystem.app.services;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Organization;
import com.coursesystem.app.payload.forms.OrganizationForm;

public interface OrganizationService {
    
    public Organization findById(Long id) throws nonExistentIdException;
    
    public Organization chargeFormData(OrganizationForm orgForm) throws nonExistentIdException;

    public Organization findByIdAndCategory(Long id, String category) throws nonExistentIdException;

    public Organization changeOrgStatus(Long id, String status) throws nonExistentIdException;

    public Organization update(OrganizationForm orgForm, Long id) throws nonExistentIdException;
}
