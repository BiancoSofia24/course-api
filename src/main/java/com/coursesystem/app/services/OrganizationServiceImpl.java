package com.coursesystem.app.services;

import java.util.Optional;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Agent;
import com.coursesystem.app.models.Organization;
import com.coursesystem.app.payload.forms.OrganizationForm;
import com.coursesystem.app.repository.AgentRepository;
import com.coursesystem.app.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private AgentRepository agentRepo;

    @Autowired 
    private OrganizationRepository orgRepo;

    public Iterable<Organization> findAll() {
        return orgRepo.findAll();
    }

    public Organization save(Organization org) {
        this.orgRepo.save(org);
        return org;
    }

    public void delete(Organization org) {
        this.orgRepo.delete(org);
    }

    @Override
    public Organization findById(Long id) throws nonExistentIdException {
        Optional<Organization> optionalOrg = orgRepo.findById(id);
        if (Optional.empty().equals(optionalOrg)) {
            throw new nonExistentIdException("The given id doesn't exist");
        }
        Organization org = optionalOrg.get();
        return org;
    }

    @Override
    public Organization chargeFormData(OrganizationForm orgForm, Organization org) throws nonExistentIdException {
        Optional<Agent> optionalAgent = agentRepo.findById(orgForm.getAgentId());
        if (Optional.empty().equals(optionalAgent)) {
            throw new nonExistentIdException("The given id doesn't exist");
        }   
        Agent agent = optionalAgent.get();

        org.setName(orgForm.getName());
        org.setCuil(orgForm.getCuil());
        org.setType(orgForm.getType());
        org.setAddress(orgForm.getAddress());
        org.setCategory(orgForm.getCategory());
        org.setFoundationYear(orgForm.getFoundationYear());
        org.setContactNumber(orgForm.getContactNumber());
        org.setOrgStatus(orgForm.getOrgStatus());
        org.setAgent(agent);
        return org;
    }

    @Override
    public Organization findByIdAndCategory(Long id, String category) throws nonExistentIdException {
        return null;
    }
    
}
