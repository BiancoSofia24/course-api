package com.coursesystem.app.controllers;

import com.coursesystem.app.exceptions.invalidStatusException;
import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Organization;
import com.coursesystem.app.payload.forms.OrganizationForm;
import com.coursesystem.app.services.OrganizationServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    @GetMapping(path = "/")
    @Operation(summary = "Organizations List", description = "Lists all the organizations in the data base")
    // @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public ResponseEntity<Iterable<Organization>> findAll() {
        try {
            log.info("Find all organizations");
            return new ResponseEntity<>(orgServImpl.findAll(), HttpStatus.OK);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/")
    @Operation(summary = "New organization", description = "Add a new organization to the database.")
    // @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Organization> add(@RequestBody OrganizationForm orgForm) {
        log.info("Create a new organization");
        try {
            log.info("Requesting data...");
            
            Organization org = orgServImpl.chargeFormData(orgForm);
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
    @PutMapping(path = "/{id}")
	@Operation(summary = "Update organization", description = "Find an organization by its ID and then update the info.")
	// @PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<Organization> update(@PathVariable Long id, @RequestBody OrganizationForm orgForm) {

		log.info("Update an organization");
		try {
            log.info("Finding...");

			Organization org = orgServImpl.update(orgForm, id);
			log.info("Updating...");

			orgServImpl.save(org);
			log.info("Organization updated!");

			return new ResponseEntity<>(org, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

    /**
     * Delete organization
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete organization", description = "Find an organization by its ID and then delete the organization.")
	// @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Deleting an organization");
        try {
            log.info("Finding...");

            Organization org = orgServImpl.findById(id);
            log.info("Organization finded! Deleting...");

            orgServImpl.delete(org);
            log.info("Organization deleted!");

            return new ResponseEntity<>("Organization deleted!", HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>("The given id doesn't exists", HttpStatus.BAD_REQUEST);

        } catch (invalidStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Organization can't be deleted. Status must be CANCELLED", HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Find by ID
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    @Operation(summary = "Find by ID", description = "Find an organization by its ID")
    public ResponseEntity<Organization> findById(@PathVariable Long id) {
        log.info("Find an organization by the ID: " + id);
        try {
            log.info("Finding...");

            Organization org = orgServImpl.findById(id);
            log.info("Organization finded!");

            return new ResponseEntity<>(org, HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update organization status
     * @param id
     * @return
     */
    @PutMapping(path = "/status/{id}")
	@Operation(summary = "Update organization status", description = "Find an organizations by its ID and then update the organization status.")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> changeOrgStatus(@PathVariable Long id, @RequestParam String status) {
        log.info("Update organization status");
        try {
            log.info("Finding...");
        
            Organization org = orgServImpl.changeOrgStatus(id, status);
			log.info("Organization finded. Updating organization status...");

            orgServImpl.save(org);
            log.info("Organization status updated!");

            return new ResponseEntity<>(org, HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>("The give id doesn't exists", HttpStatus.BAD_REQUEST);
        }
    }
}

