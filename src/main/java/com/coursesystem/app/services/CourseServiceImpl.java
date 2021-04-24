package com.coursesystem.app.services;

import java.util.Optional;

import com.coursesystem.app.enums.ECourseStatus;
import com.coursesystem.app.enums.EStatus;
import com.coursesystem.app.exceptions.invalidStatusException;
import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Course;
import com.coursesystem.app.models.Organization;
import com.coursesystem.app.payload.forms.CourseForm;
import com.coursesystem.app.repository.CourseRepository;
import com.coursesystem.app.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private OrganizationRepository orgRepo;

    @Autowired
    private CourseRepository courseRepo;

    public Iterable<Course> findAll() {
        return this.courseRepo.findAll();
    }

    public Course save(Course course) throws invalidStatusException {
        if (course.getOrg().getOrgStatus() == EStatus.APPROVED && course.getCourseStatus() == null) {
            course.setCourseStatus(ECourseStatus.ENROLLMENT);
        } else if (course.getCourseStatus() != null) {
            course.setCourseStatus(course.getCourseStatus());
        } else {
        throw new invalidStatusException("Course can't be created. Organization status must be APPROVED");
        }
        
        this.courseRepo.save(course);
        return course;
    }

    public void delete(Course course) throws invalidStatusException {
        if (course.getCourseStatus() != ECourseStatus.IN_PROGRESS) {
            this.courseRepo.delete(course);
        } else {
            throw new invalidStatusException("Can't be deleted. Status must be ENROLLMENT or FINALIZED");
        }
    }

    @Override
    public Course findById(Long id) throws nonExistentIdException {
        Optional<Course> optionalCourse = courseRepo.findById(id);

        if (Optional.empty().equals(optionalCourse)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }
        Course course = optionalCourse.get();
        return course;
    }

    @Override
    public Course chargeFormData(CourseForm courseForm) throws nonExistentIdException {
        Optional<Organization> optionalOrg = orgRepo.findById(courseForm.getOrgId());

        if (Optional.empty().equals(optionalOrg)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Course course = new Course();
        Organization org = optionalOrg.get();

        course.setName(courseForm.getName());
        course.setDescription(courseForm.getDescription());
        course.setModality(courseForm.getModality());
        course.setCost(courseForm.getCost());
        course.setHours(courseForm.getHours());
        course.setCategory(courseForm.getCategory());
        course.setQuotas(courseForm.getQuotas());
        course.setScholarshipQuotas(courseForm.getScholarshipQuotas());
        course.setOrg(org);
        return course;
    }

    public Iterable<Course> findByCategory(String category) {
        return this.courseRepo.findByCategory(category);
    }

    public Iterable<Course> findByOrg(Long orgId) throws nonExistentIdException {
        Optional<Organization> optionalOrg = orgRepo.findById(orgId);

        if (Optional.empty().equals(optionalOrg)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Organization org = optionalOrg.get();
        return this.courseRepo.findByOrg(org);
    }

    public Iterable<Course> findByCourseStatus(String status) {

        switch (status.toUpperCase()) {
        case "ENROLLMENT":
            return this.courseRepo.findByCourseStatus(ECourseStatus.ENROLLMENT);
        case "IN PROGRESS":
            return this.courseRepo.findByCourseStatus(ECourseStatus.IN_PROGRESS);
        case "FINALIZED":
            return this.courseRepo.findByCourseStatus(ECourseStatus.FINALIZED);
        default:
            return null;
        }
    }

    @Override
    public Course update(CourseForm courseForm, Long id) throws nonExistentIdException {
        Optional<Course> optionalCourse = courseRepo.findById(id);

        if (Optional.empty().equals(optionalCourse)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Course course = optionalCourse.get();
        course.setName(courseForm.getName());
        course.setDescription(courseForm.getDescription());
        course.setModality(courseForm.getModality());
        course.setCost(courseForm.getCost());
        course.setHours(courseForm.getHours());
        course.setCategory(courseForm.getCategory());
        course.setQuotas(courseForm.getQuotas());
        course.setScholarshipQuotas(courseForm.getScholarshipQuotas());

        return course;
    }

    // This method doesn't override the course status. Remains allways in ENROLLMENT
    @Override
    public Course changeCourseStatus(Long id, String status) throws nonExistentIdException {
        Optional<Course> optionalCourse = courseRepo.findById(id);

        if (Optional.empty().equals(optionalCourse)) {
            throw new nonExistentIdException("The given id doesn't exists");
        }

        Course course = optionalCourse.get();

        switch (status.toUpperCase()) {
        case "ENROLLMENT":
            course.setCourseStatus(ECourseStatus.ENROLLMENT);
            break;
        case "IN_PROGRESS":
            course.setCourseStatus(ECourseStatus.IN_PROGRESS);
            break;
        case "FINALIZED":
            course.setCourseStatus(ECourseStatus.FINALIZED);
            break;
        default:
            break;
        }

        return course;
    }

    @Override
    public Iterable<Course> findByCategoryAndOrganization(String category, Long orgId) {

        try {
            if (orgId == 0) {
                return this.findByCategory(category);
            } else if (category == null) {
                return this.findByOrg(orgId);
            } else {
                // Print all the courses by that specific ogr. Leaving for review
                Iterable<Course> courseList = this.findByCategory(category);
                courseList = this.findByOrg(orgId);
                return courseList;
            }
        } catch (nonExistentIdException e) {
            e.printStackTrace();
            return null;
        }
    }

}
