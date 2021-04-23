package com.coursesystem.app.services;

import java.util.List;

import com.coursesystem.app.enums.ECourseStatus;
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

    @Override
    public Course findById(Long id) throws nonExistentIdException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Course chargeFormCourse(CourseForm courseForm, Course course) throws nonExistentIdException {
        // TODO Auto-generated method stub
        return null;
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
