package com.coursesystem.app.controllers;

import com.coursesystem.app.exceptions.invalidStatusException;
import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Course;
import com.coursesystem.app.payload.forms.CourseForm;
import com.coursesystem.app.services.CourseServiceImpl;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(path = "/app/courses")
public class CourseController {
    
    private static final Logger log = LoggerFactory.getLogger(CourseController.class); 

    @Autowired
    private CourseServiceImpl courseServImpl;

    /**
     * Find all courses
     * @return
     */
    @GetMapping(path = "/")
    @Operation(summary = "Courses List", description = "Lists all the courses in the data base")
    public ResponseEntity<Iterable<Course>> findAll() {
        try {
            log.info("Find all courses");
            return new ResponseEntity<>(courseServImpl.findAll(), HttpStatus.OK);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Create a new course. Organizations status must be APPROVED
     * @param courseForm
     * @return
     */
    @PostMapping(path = "/")
    @Operation(summary = "New course", description = "Add a new course to the database.")
    // @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Course> add(@RequestBody CourseForm courseForm) {
        log.info("Create a new course");
        try {
            log.info("Requesting data...");

            Course course = courseServImpl.chargeFormData(courseForm);
            log.info("Creating...");

            courseServImpl.save(course);
            log.info("Course created!");

            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            
        } catch (invalidStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Update a course
     * @param repForm
     * @return
     */
    @PutMapping(path = "/{id}")
	@Operation(summary = "Update course", description = "Find a course by its ID and then update the info.")
	// @PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<Course> update(@PathVariable Long id, @RequestBody CourseForm courseForm) {

		log.info("Update a course");
		try {
            log.info("Finding...");

			Course course = courseServImpl.update(courseForm, id);
			log.info("Updating...");

			courseServImpl.save(course);
			log.info("Course updated!");

			return new ResponseEntity<>(course, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		} catch (invalidStatusException e) {
            e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

	}

    /**
     * Delete course. Status must be different than IN PROGRESS
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete course", description = "Find a course by its ID and then delete the organization.")
	// @PreAuthorize("hasRole('AGENT') or hasRole('ADMIN')")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        log.info("Deleting an organization");
        try {
            log.info("Finding...");

            Course course = courseServImpl.findById(id);
            log.info("Course finded! Deleting...");

            courseServImpl.delete(course);
            log.info("Organization deleted!");

            return new ResponseEntity<>("Course deleted!", HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>("The given id doesn't exists", HttpStatus.BAD_REQUEST);

        } catch (invalidStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Course can't be deleted. Status must be ENROLLMENT or FINALIZED", HttpStatus.FORBIDDEN);
        }
    }

    /**
     * Find by ID
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    @Operation(summary = "Find by ID", description = "Find a course by its ID")
    public ResponseEntity<Course> findById(@PathVariable Long id) {
        log.info("Find an organization by the ID: " + id);
        try {
            log.info("Finding...");

            Course course = courseServImpl.findById(id);
            log.info("Organization finded!");

            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update course status
     * @param id
     * @return
     */
    @PutMapping(path = "/status/{id}")
	@Operation(summary = "Update course status", description = "Find a course by its ID and then update the course status.")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> changeCourseStatus(@PathVariable Long id, @RequestParam String status) {
        log.info("Update course status");
        try {
            log.info("Finding...");
        
			Course course = courseServImpl.changeCourseStatus(id, status);
			log.info("Course finded. Updating course status...");

            courseServImpl.save(course);
            log.info("Course status updated!");

            return new ResponseEntity<>(course, HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>("The give id doesn't exists", HttpStatus.BAD_REQUEST);
        } catch (invalidStatusException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Find all courses by category and/or Organization
     * @param category
     * @return
     */
    @GetMapping(path = "/catg&org")
    @Operation(summary = "Courses List By Category and/or Organization", description = "Lists all the courses by category and/or Organization")
    public ResponseEntity<Iterable<Course>> findByCategoryAndOrganization(@RequestParam String category, @RequestParam Long orgId) {
        try {
            log.info("Find all courses by category and/or organizations");
            return new ResponseEntity<>(courseServImpl.findByCategoryAndOrganization(category, orgId), HttpStatus.OK);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Find all courses by category and/or Organization
     * @param category
     * @return
     */
    @GetMapping(path = "/status")
    @Operation(summary = "Courses List By Category and/or Organization", description = "Lists all the courses by category and/or Organization")
    public ResponseEntity<Iterable<Course>> findByCourseStatus(@RequestParam String status) {
        try {
            log.info("Find all courses by category and/or organizations");
            return new ResponseEntity<>(courseServImpl.findByCourseStatus(status), HttpStatus.OK);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
