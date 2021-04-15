package com.coursesystem.app.controllers;

// import java.util.HashSet;
import java.util.List;
// import java.util.Optional;
// import java.util.Set;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Agent;
//import com.coursesystem.app.models.Role;
import com.coursesystem.app.models.User;
import com.coursesystem.app.payload.forms.AgentForm;
//import com.coursesystem.app.repository.RoleRepository;
import com.coursesystem.app.services.AgentsServiceImpl;
import com.coursesystem.app.services.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping(path = "app/agents")
public class AgentController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    
    @Autowired
    private UserServiceImpl userServImpl;

    @Autowired
    private AgentsServiceImpl agentServImpl;

    // @Autowired
    // private RoleRepository roleRepo;

    /**
     * Find all users
     * @return
     */
    @GetMapping(path = "/all")
    @Operation(summary = "Agents List", description = "Lists all the agents users that exist in the database.")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Agent>> findAll() {
        log.info("Find all agents users");
        return new ResponseEntity<>(agentServImpl.findAll(), HttpStatus.OK);
    }

    /**
     * New agent user
     * @param id
     * @param name
     * @param lastname
     * @param documentType
     * @param documentNumber
     * @param job
     * @return
     */
    @PostMapping(path = "/new")
    @Operation(summary = "New agent user", description = "Add a new agent users to the database.")
    // @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Agent> add(@RequestParam Long id, @RequestParam String name, @RequestParam String lastname, @Parameter(description = "DNI | PAS | CI") @RequestParam String documentType, @RequestParam Integer documentNumber, @RequestParam String job) {
        log.info("Create a new agent user");
        Agent agent = new Agent();
        AgentForm agentForm = new AgentForm();
        try {
            log.info("Requesting data...");
            
            User agentUser = userServImpl.findById(id);
            agentForm.setId(agentUser.getId());
            agentForm.setName(name);
            agentForm.setLastname(lastname);
            agentForm.setDocumentType(documentType);
            agentForm.setDocumentNumber(documentNumber);
            agentForm.setJob(job);

            // Missing role for the created user
            // Optional<Role> role = roleRepo.findById(2L);
            // role.get().getUserRole();
            // Set<Role> set = new HashSet<Role>();
            // set.add(role.get());
            // agentUser.setRole(set);

            agent = agentServImpl.chargeFormData(agentForm, agent);
            log.info("Creating...");
            agentServImpl.save(agent);
            log.info("Agent user created!");
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
    @DeleteMapping(path = "/delete/{id}")
	@Operation(summary = "Delete agent user", description = "Find a user by its ID and then delete the agent user.")
	// @PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<Object> borrarRep(@PathVariable Long id) {

		log.info("Delete an agent user");
		Agent agent;
		try {
            log.info("Finding...");
			agent = agentServImpl.findById(id);
			log.info("User finded. Deleting...");
			agentServImpl.delete(agent);
            userServImpl.delete(agent.getUser());
			log.info("User deleted!");
			return new ResponseEntity<>(null, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

    /**
     * Update an agent user
     * @param repForm
     * @return
     */
    @PutMapping(path = "/update/{id}")
	@Operation(summary = "Update agent", description = "Find an agent user by its ID and then update the user info.")
	// @PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<Agent> modificarRep(@RequestParam Long id, @RequestParam String name, @RequestParam String lastname, @Parameter(description = "DNI | PAS | CI") @RequestParam String documentType, @RequestParam Integer documentNumber, @RequestParam String job) {

		log.info("Metodo modificarRep: buscando rep...");
        AgentForm agentForm = new AgentForm();
		try {
            log.info("Finding...");
            User agentUser = userServImpl.findById(id);
            Agent agent = agentServImpl.findById(agentUser.getId());
            log.info("User finded!");
            
            agentForm.setId(agentUser.getId());
            agentForm.setName(name);
            agentForm.setLastname(lastname);
            agentForm.setDocumentType(documentType);
            agentForm.setDocumentNumber(documentNumber);
            agentForm.setJob(job);

			log.info("Updating...");
			agent = agentServImpl.chargeFormData(agentForm, agent);
			agentServImpl.save(agent);
			log.info("User updated!");
			return new ResponseEntity<>(agent, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}
}
