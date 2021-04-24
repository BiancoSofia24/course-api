package com.coursesystem.app.services;

import java.util.Optional;

import com.coursesystem.app.enums.EStatus;
import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.exceptions.invalidStatusException;
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
        return this.orgRepo.findAll();
    }

    public Organization save(Organization org) {
        if (org.getOrgStatus() == null) {
            org.setOrgStatus(EStatus.AWAITING_APPROVAL);
        }
        this.orgRepo.save(org);
        return org;
    }

    public void delete(Organization org) throws invalidStatusException {
        if (org.getOrgStatus() == EStatus.CANCELLED) {
            this.orgRepo.delete(org);
        } else {
            throw new invalidStatusException("Can't be deleted. Status must be CANCELLED");
        }
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
    public Organization chargeFormData(OrganizationForm orgForm) throws nonExistentIdException {
        Optional<Agent> optionalAgent = agentRepo.findById(orgForm.getAgentId());

        if (Optional.empty().equals(optionalAgent)) {
            throw new nonExistentIdException("The given id doesn't exist");
        }

        Organization org = new Organization();
        Agent agent = optionalAgent.get();

        org.setName(orgForm.getName());
        org.setCuil(orgForm.getCuil());
        org.setType(orgForm.getType());
        org.setAddress(orgForm.getAddress());
        org.setCategory(orgForm.getCategory());
        org.setFoundationYear(orgForm.getFoundationYear());
        org.setContactNumber(orgForm.getContactNumber());
        org.setAgent(agent);
        return org;
    }

    @Override
    public Organization findByIdAndCategory(Long id, String category) throws nonExistentIdException {
        // To do
        return null;
    }

    @Override
    public Organization changeOrgStatus(Long id, String status) throws nonExistentIdException {
        Optional<Organization> optionalOrg = orgRepo.findById(id);

        if (Optional.empty().equals(optionalOrg)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Organization org = optionalOrg.get();

        switch (status.toUpperCase()) {
        case "APPROVED":
            org.setOrgStatus(EStatus.APPROVED);
            break;
        case "REJECTED":
            org.setOrgStatus(EStatus.REJECTED);
            break;
        case "CANCELLED":
            org.setOrgStatus(EStatus.CANCELLED);
            break;
        default:
            break;
        }

        return org;
    }

    @Override
    public Organization update(OrganizationForm orgForm, Long id) throws nonExistentIdException {
        Optional<Organization> optionalOrg = orgRepo.findById(id);

        if (Optional.empty().equals(optionalOrg)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Organization org = optionalOrg.get();
        org.setName(orgForm.getName());
        org.setCuil(orgForm.getCuil());
        org.setType(orgForm.getType());
        org.setAddress(orgForm.getAddress());
        org.setCategory(orgForm.getCategory());
        org.setFoundationYear(orgForm.getFoundationYear());
        org.setContactNumber(orgForm.getContactNumber());

        return org;
    }

}
