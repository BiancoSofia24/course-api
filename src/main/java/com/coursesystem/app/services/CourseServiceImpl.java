package com.coursesystem.app.services;

import java.util.List;
import java.util.Optional;

import com.coursesystem.app.enums.ECourseStatus;
import com.coursesystem.app.enums.EStatus;
import com.coursesystem.app.exceptions.invalidStatusException;
import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Course;
import com.coursesystem.app.models.Organization;
import com.coursesystem.app.models.Student;
import com.coursesystem.app.payload.forms.CourseForm;
import com.coursesystem.app.repository.AgentRepository;
import com.coursesystem.app.repository.CourseRepository;
import com.coursesystem.app.repository.OrganizationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private AgentRepository agentRepo;

    @Autowired 
    private OrganizationRepository orgRepo;

    @Autowired
    private CourseRepository courseRepo;

    public Iterable<Course> findAll() {
        return this.courseRepo.findAll();
    }

    public Course save(Course course) throws invalidStatusException {
        if (course.getOrg().getOrgStatus() == EStatus.APPROVED) {
            this.courseRepo.save(course);
            return course;
        } else {
            throw new invalidStatusException("Course can't be created. Organization status must be APPROVED");
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
        Optional<Organization> optionalOrg = orgRepo.findById(courseForm.getOrgID());

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
        course.setQuotas(courseForm.getQuotas());
        course.setScholarshipQuotas(courseForm.getScholarshipQuotas());
        course.setCourseStatus(courseForm.getCourseStatus());
        course.setOrg(org);
        return course;
    }

    @Override
    public List<Course> findByCategory(String category) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Course> findByStudentAndInProgressStatus(Student student, ECourseStatus courseStatus) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Course> findByStudentAndFinalizedStatus(Student student, ECourseStatus courseStatus) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Course> findByCategoryAndOrganization(String category, Organization org) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
