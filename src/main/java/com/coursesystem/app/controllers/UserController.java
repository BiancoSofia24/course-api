package com.coursesystem.app.controllers;

import java.util.List;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.forms.UserForm;
import com.coursesystem.app.models.User;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserServiceImpl userServImpl;

    /**
     * Find all users
     * @return
     */
    @GetMapping(path = "/all")
    @Operation(summary = "Users List", description = "Lists all the users that exist in the database.")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> findAll() {
        log.info("Find all users");
        return new ResponseEntity<>(userServImpl.findAll(), HttpStatus.OK);
    }

    /**
     * Update user
     * @param id
     * @param username
     * @param email
     * @param password
     * @return
     */
    @PutMapping(path = "/update")
    @Operation(summary = "Update user", description = "Find a user by its ID and then update the user info.")
    //public ResponseEntity<User> update(@RequestBody UserForm userForm) {
    public ResponseEntity<User> update(@RequestParam Long id, @RequestParam String username, @RequestParam String email, @RequestParam String password) {
        log.info("Updating an user");
        User user;
        try {
            log.info("Finding...");
            // Error. Doesn't find any ID (The given id must not be null!)
            //user = userServImpl.findById(userForm.getId());
            user = userServImpl.findById(id);
            log.info("User finded!");

            UserForm userForm = new UserForm();
            userForm.setId(user.getId());
            userForm.setUsername(username);
            userForm.setEmail(email);
            userForm.setPassword(password);

            user = userServImpl.chargeFormData(userForm, user);
            log.info("Updating...");
            userServImpl.save(user);
            log.info("User updated!");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }

    /**
     * Delete user
     * @param id
     * @return
     */
    @DeleteMapping(path = "/delete/{id}")
    @Operation(summary = "Delete user", description = "Find a user by its ID and then delete the user.")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Deleting an user");
        User user;
        try {
            log.info("Finding...");
            user = userServImpl.findById(id);
            log.info("User finded! Deleting...");
            userServImpl.delete(user);
            log.info("User deleted!");
            return new ResponseEntity<>(null, HttpStatus.OK);
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
    @GetMapping(path = "/find/{id}")
    @Operation(summary = "Find by ID", description = "Find a user by its ID")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        log.info("Find a user by the ID: " + id);
        User user;
        try {
            user = userServImpl.findById(id);
            log.info("User finded");
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/new")
    @Operation(summary = "Create user", description = "Create a new user into the database.")
    public ResponseEntity<User> add(@RequestParam String username, @RequestParam String email, @RequestParam String password) {
        log.info("Creating an user");
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        log.info("Creating...");
        user = userServImpl.save(user);
        log.info("User created!");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
