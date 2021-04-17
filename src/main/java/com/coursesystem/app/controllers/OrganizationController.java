package com.coursesystem.app.controllers;

import com.coursesystem.app.enums.EStatus;
import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Organization;
import com.coursesystem.app.payload.forms.OrganizationForm;
import com.coursesystem.app.services.OrganizationServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/app/orgs")
public class OrganizationController {
    
    private static final Logger log = LoggerFactory.getLogger(OrganizationController.class); 

    @Autowired
    private OrganizationServiceImpl orgServImpl;

    /**
     * Find all organizations
     * @return
     */
    @GetMapping(path = "/all")
    @Operation(summary = "Organizations List", description = "Lists all the organizations in the data base")
    // @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public ResponseEntity<Iterable<Organization>> findAll() {
        log.info("Find all organizations");
        return new ResponseEntity<>(orgServImpl.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/new")
    @Operation(summary = "New organization", description = "Add a new organization to the database.")
    // @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Organization> add(@RequestParam String name, @RequestParam Long cuil, @RequestParam String type, @RequestParam String address, @RequestParam String category, @RequestParam Integer foundationYear, @RequestParam Integer contactNumber, @RequestParam Long agentId) {
        log.info("Create a new organization");
        Organization org = new Organization();
        OrganizationForm orgForm = new OrganizationForm();
        try {
            log.info("Requesting data...");
            
            orgForm.setName(name);
            orgForm.setCuil(cuil);
            orgForm.setType(type);
            orgForm.setAddress(address);
            orgForm.setCategory(category);
            orgForm.setFoundationYear(foundationYear);
            orgForm.setContactNumber(contactNumber);
            orgForm.setOrgStatus(EStatus.AWAITING_APPROVAL);
            orgForm.setAgentId(agentId);

            log.info("Validating...");
            org = orgServImpl.chargeFormData(orgForm, org);
            log.info("Creating...");
            orgServImpl.save(org);
            log.info("Organization created!");
            return new ResponseEntity<>(org, HttpStatus.CREATED);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an organization
     * @param repForm
     * @return
     */
    @PutMapping(path = "/update/{id}")
	@Operation(summary = "Update organization", description = "Find anorganization by its ID and then update the info.")
	// @PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<Organization> modificarRep(@PathVariable Long id, @RequestParam String name, @RequestParam Long cuil, @RequestParam String type, @RequestParam String address, @RequestParam String category, @RequestParam Integer foundationYear, @RequestParam Integer contactNumber, @RequestParam Long agentId) {

		log.info("Update an organization");
        OrganizationForm orgForm = new OrganizationForm();
		try {
            log.info("Finding...");
            Organization org = orgServImpl.findById(id);
            log.info("Organization finded!");
            
            orgForm.setName(name);
            orgForm.setCuil(cuil);
            orgForm.setType(type);
            orgForm.setAddress(address);
            orgForm.setCategory(category);
            orgForm.setFoundationYear(foundationYear);
            orgForm.setContactNumber(contactNumber);
            orgForm.setAgentId(agentId);

			log.info("Updating...");
			org = orgServImpl.chargeFormData(orgForm, org);
			orgServImpl.save(org);
			log.info("Organization updated!");
			return new ResponseEntity<>(org, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}
}

