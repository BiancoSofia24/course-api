package com.coursesystem.app.controllers;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Role;
import com.coursesystem.app.models.Student;
import com.coursesystem.app.models.User;
import com.coursesystem.app.payload.forms.SocioEconomicForm;
import com.coursesystem.app.payload.forms.StudentForm;
import com.coursesystem.app.repository.RoleRepository;
import com.coursesystem.app.services.StudentServiceImpl;
import com.coursesystem.app.services.UserServiceImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@Service
@RequestMapping(path = "app/students")
public class StudentController {

    private static final Logger log = LoggerFactory.getLogger(StudentController.class);
    
    @Autowired
    private UserServiceImpl userServImpl;

    @Autowired
    private StudentServiceImpl studentServImpl;

    @Autowired
    private RoleRepository roleRepo;

    /**
     * Find all students
     * @return
     */
    @GetMapping(path = "/")
    @Operation(summary = "Students List", description = "Lists all the students that exits in the database")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Iterable<Student>> findAll() {
        log.info("Find all students");
        return new ResponseEntity<>(studentServImpl.findAll(), HttpStatus.OK);
    }

    /**
     * Find by ID
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    @Operation(summary = "Find by ID", description = "Find an student by its ID")
    public ResponseEntity<Student> findById(@PathVariable Long id) {
        log.info("Find an student by the ID: " + id);
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
    @Operation(summary = "New agent", description = "Add a new agent to the database.")
    // @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Student> add(@RequestParam Long id, @RequestParam String name, @RequestParam String lastname,@Parameter(description = "dd/MM/yyyy") @RequestParam String birthday, @Parameter(description = "F | M") @RequestParam String gender, @RequestParam String location) {
        log.info("Create a new agent");
        Student student = new Student();
        StudentForm studentForm = new StudentForm();
        try {
            log.info("Requesting data...");
            
            User studentUser = userServImpl.findById(id);
            studentForm.setId(studentUser.getId());
            studentForm.setName(name);
            studentForm.setLastname(lastname);
            studentForm.setBirthday(birthday);
            studentForm.setGender(gender);
            studentForm.setLocation(location);

            // Missing role for the created user
            Optional<Role> role = roleRepo.findById(3L);
            role.get().getUserRole();
            Set<Role> set = new HashSet<Role>();
            set.add(role.get());
            studentUser.setRole(set);

            log.info("Validating...");

            student = studentServImpl.chargeFormData(studentForm, student);
            log.info("Creating...");

            studentServImpl.save(student);
            log.info("Student created!");

            return new ResponseEntity<>(student, HttpStatus.CREATED);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Update an student user
     * @param id
     * @param name
     * @param lastname
     * @param documentType
     * @param documentNumber
     * @param job
     * @return
     */
    @PutMapping(path = "/update/{id}")
	@Operation(summary = "Update student", description = "Find an student by its ID and then update the student info.")
	// @PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<Student> update(@PathVariable Long id, @RequestParam String name, @RequestParam String lastname,@Parameter(description = "dd/MM/yyyy") @RequestParam String birthday, @Parameter(description = "F | M") @RequestParam String gender, @RequestParam String location) {

		log.info("Update an agent");
        StudentForm studentForm = new StudentForm();
		try {
            log.info("Finding...");

            User studentUser = userServImpl.findById(id);
            Student student = studentServImpl.findById(studentUser.getId());
            log.info("Student finded!");
            
            studentForm.setId(studentUser.getId());
            studentForm.setName(name);
            studentForm.setLastname(lastname);
            studentForm.setBirthday(birthday);
            studentForm.setGender(gender);
            studentForm.setLocation(location);
			log.info("Updating...");

			student = studentServImpl.chargeFormData(studentForm, student);
			studentServImpl.save(student);
			log.info("Student updated!");

			return new ResponseEntity<>(student, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}

	}

    /**
     * Update an student user with the socialeconomic data
     * @param id
     * @param name
     * @param lastname
     * @param documentType
     * @param documentNumber
     * @param job
     * @return
     */
    @PutMapping(path = "/se-data/{id}")
	@Operation(summary = "Update socioeconomic info", description = "Find an student by its ID and then update the student info with the socialeconomic data.")
	// @PreAuthorize("hasRole('AGENT')")
	public ResponseEntity<Student> updateSocioEconomicData(@PathVariable Long id, @Parameter(description = "0 for NO | 1 for YES") @RequestParam Integer studying, @Parameter(description = "0 for NO | 1 for YES") @RequestParam Integer working, @RequestParam Float income, @Parameter(description = "0 for NO | 1 for YES") @RequestParam Integer familyInCharge, @RequestParam Integer dependents) {

		log.info("Update an agent");
        SocioEconomicForm seForm = new SocioEconomicForm();
		try {
            log.info("Finding...");

            Student student = studentServImpl.findById(id);
            log.info("Student finded!");
            
            seForm.setId(id);
            seForm.setStudying(studying);
            seForm.setWorking(working);
            seForm.setIncome(income);
            seForm.setFamilyInCharge(familyInCharge);
            seForm.setDependents(dependents);
			log.info("Updating...");

			student = studentServImpl.chargeSEFormData(seForm, student);
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
    @DeleteMapping(path = "/delete/{id}")
	@Operation(summary = "Delete student", description = "Find a user by its ID and then delete the student.")
	// @PreAuthorize("hasRole('STUDENT') or hasRole('ADMIN')")
	public ResponseEntity<Object> delete(@PathVariable Long id) {

		log.info("Delete an student");
		Student student;
		try {
            log.info("Finding...");
        
			student = studentServImpl.findById(id);
			log.info("Student finded. Deleting...");

			studentServImpl.delete(student);
            userServImpl.delete(student.getUser());
			log.info("Student deleted!");

			return new ResponseEntity<>(null, HttpStatus.OK);

		} catch (nonExistentIdException e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}
}
