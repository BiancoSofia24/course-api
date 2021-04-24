package com.coursesystem.app.controllers;

import java.util.List;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Agent;
import com.coursesystem.app.payload.forms.AgentForm;
import com.coursesystem.app.services.AgentsServiceImpl;
import com.coursesystem.app.services.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "app/agents")
public class AgentController {

    private static final Logger log = LoggerFactory.getLogger(AgentController.class);
    
    @Autowired
    private UserServiceImpl userServImpl;

    @Autowired
    private AgentsServiceImpl agentServImpl;

    /**
     * Find all agents
     * @return
     */
    @GetMapping(path = "/")
    @Operation(summary = "Agents List", description = "Lists all the agents that exist in the database.")
    // @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public ResponseEntity<List<Agent>> findAll() {
        try {
            log.info("Find all agents");
            return new ResponseEntity<>(agentServImpl.findAll(), HttpStatus.OK);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * New agent user
     * @param agentForm
     * @return
     */
    @PostMapping(path = "/")
    @Operation(summary = "New agent", description = "Add a new agent to the database.")
    // @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Agent> add(@RequestBody AgentForm agentForm) {
        log.info("Create a new agent");
        try {
            log.info("Requesting data...");

            Agent agent = agentServImpl.chargeFormData(agentForm);
            log.info("Validating...");

            agentServImpl.save(agent);
            log.info("Agent created!");

            return new ResponseEntity<>(agent, HttpStatus.CREATED);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Delete an agent user
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
	@Operation(summary = "Delete agent", description = "Find a user by its ID and then delete the agent.")
	// @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable Long id) {

		log.info("Delete an agent");
		try {
            log.info("Finding...");
        
			Agent agent = agentServImpl.findById(id);
			log.info("Agent finded. Deleting...");

			agentServImpl.delete(agent);
            userServImpl.delete(agent.getUser());
			log.info("Agent deleted!");

			return new ResponseEntity<>("Agent deleted!", HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>("The given id doesn't exists", HttpStatus.BAD_REQUEST);
		}
	}

    /**
     * Update an agent user
     * @param id
     * @param agentForm
     * @return
     */
    @PutMapping(path = "/{id}")
	@Operation(summary = "Update agent", description = "Find an agent by its ID and then update the agent info.")
	// @PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<Agent> update(@PathVariable Long id, @RequestBody AgentForm agentForm) {

		log.info("Update an agent");
		try {
            log.info("Finding...");

			Agent agent = agentServImpl.update(agentForm, id);
			log.info("Updating...");

			agentServImpl.save(agent);
			log.info("Agent updated!");

			return new ResponseEntity<>(agent, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

    /**
     * Find by ID
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    @Operation(summary = "Find by ID", description = "Find an agent by its ID")
    public ResponseEntity<Agent> findById(@PathVariable Long id) {
        log.info("Find an agent by the ID: " + id);
        try {
            log.info("Finding...");

            Agent agent = agentServImpl.findById(id);
            log.info("Agent finded!");
            
            return new ResponseEntity<>(agent, HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
