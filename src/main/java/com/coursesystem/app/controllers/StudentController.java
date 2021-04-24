package com.coursesystem.app.controllers;


import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Student;
import com.coursesystem.app.payload.forms.SocioEconomicForm;
import com.coursesystem.app.payload.forms.StudentForm;
import com.coursesystem.app.services.StudentServiceImpl;
import com.coursesystem.app.services.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@Service
@RequestMapping(path = "app/students")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    
    @Autowired
    private UserServiceImpl userServImpl;

    @Autowired
    private StudentServiceImpl studentServImpl;

    /**
     * Find all students
     * @return
     */
    @GetMapping(path = "/")
    @Operation(summary = "Students List", description = "Lists all the students that exits in the database")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Student>> findAll() {
        try {
            log.info("Find all students");
            return new ResponseEntity<>(studentServImpl.findAll(), HttpStatus.OK);
        } catch (NullPointerException e) {
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
    @Operation(summary = "Find by ID", description = "Find a student by its ID")
    public ResponseEntity<Student> findById(@PathVariable Long id) {
        log.info("Find a student by the ID: " + id);
        Student student;
        try {
            log.info("Finding...");

            student = studentServImpl.findById(id);
            log.info("Student finded!");
            
            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * New student user
     * @param studentForm
     * @return
     */
    @PostMapping(path = "/")
    @Operation(summary = "New student", description = "Add a new student to the database.")
    // @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Student> add(@RequestBody StudentForm studentForm) {
        log.info("Create a new student");
        try {
            log.info("Requesting data...");

            Student student = studentServImpl.chargeFormData(studentForm);
            log.info("Validating...");

            studentServImpl.save(student);
            log.info("Student created!");

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     * Update a student user
     * @param id
     * @param studentForm
     * @return
     */
    @PutMapping(path = "/{id}")
	@Operation(summary = "Update student", description = "Find a student by its ID and then update the student info.")
	// @PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<Student> update(@PathVariable Long id, @RequestBody StudentForm studentForm) {

		log.info("Update an student");
		try {
            log.info("Finding...");

			Student student = studentServImpl.update(studentForm, id);
			log.info("Updating...");

			studentServImpl.save(student);
			log.info("Student updated!");

			return new ResponseEntity<>(student, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

    /**
     * Update a student 
     * @param id
     * @param seForm
     * @return
     */
    @PutMapping(path = "/se-data/{id}")
	@Operation(summary = "Update socioeconomic info", description = "Find a student by its ID and then update the student info with the socioeconomic data.")
	// @PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<Student> updateSocioEconomicData(@PathVariable Long id, @RequestBody SocioEconomicForm seForm) {

		log.info("Update socioeconomic data from an student");
		try {
            log.info("Finding...");

			Student student = studentServImpl.chargeSEFormData(seForm, id);
            log.info("Validating...");

			studentServImpl.save(student);
			log.info("Student updated!");

			return new ResponseEntity<>(student, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

    /**
     * Delete an student
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
	@Operation(summary = "Delete student", description = "Find a user by its ID and then delete the student.")
	// @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable Long id) {

		log.info("Delete an student");
		try {
            log.info("Finding...");
        
			Student student = studentServImpl.findById(id);
			log.info("Student finded. Deleting...");

			studentServImpl.delete(student);
            userServImpl.delete(student.getUser());
			log.info("Student deleted!");

			return new ResponseEntity<>("Student deleted!", HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>("The given id doesn't exists", HttpStatus.BAD_REQUEST);
		}
	}

    /**
     * Update scholarship status
     * @param id
     * @return
     */
    @PutMapping(path = "/status/{id}")
	@Operation(summary = "Update scholarship status", description = "Find an user by its ID and then update the scholarship status.")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> changeScholarshipStatus(@PathVariable Long id, @RequestParam String status) {
        log.info("Update scholarship status");
        try {
            log.info("Finding...");
            
            Student student = studentServImpl.changeScholarshipStatus(id, status);
			log.info("Student finded. Updating scholarship status...");

            studentServImpl.save(student);
            log.info("Scholarship status updated!");

            return new ResponseEntity<>(student, HttpStatus.OK);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>("The given id doesn't exists", HttpStatus.BAD_REQUEST);
        }
    }
}
