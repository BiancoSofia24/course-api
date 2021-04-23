package com.coursesystem.app.controllers;

import com.coursesystem.app.enums.ECourseStatus;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
     * Find all organizations
     * @return
     */
    @GetMapping(path = "/")
    @Operation(summary = "Courses List", description = "Lists all the courses in the data base")
    // @PreAuthorize("hasRole('ADMIN') or hasRole('AGENT')")
    public ResponseEntity<Iterable<Course>> findAll() {
        log.info("Find all courses");
        return new ResponseEntity<>(courseServImpl.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/")
    @Operation(summary = "New course", description = "Add a new course to the database.")
    // @PreAuthorize("hasRole('AGENT')")
    public ResponseEntity<Course> add(@RequestParam String name, @RequestParam String description, @RequestParam String modality, @RequestParam Float cost, @RequestParam Integer hours, @RequestParam Integer quotas, @RequestParam Integer scholarshipQuotas, @RequestParam  Long orgId) {
        log.info("Create a new course");
        CourseForm courseForm = new CourseForm();
        try {
            log.info("Requesting data...");
            
            courseForm.setName(name);
            courseForm.setDescription(description);
            courseForm.setModality(modality);
            courseForm.setCost(cost);
            courseForm.setHours(hours);
            courseForm.setQuotas(quotas);
            courseForm.setScholarshipQuotas(scholarshipQuotas);
            courseForm.setCourseStatus(ECourseStatus.ENROLLMENT);
            courseForm.setOrgId(orgId);
            log.info("Validating...");

            Course course = courseServImpl.chargeFormData(courseForm);
            log.info("Creating...");

            courseServImpl.save(course);
            log.info("Course created!");

            return new ResponseEntity<>(course, HttpStatus.CREATED);
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        } catch (invalidStatusException e) {
            return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);

        }
    }
}
