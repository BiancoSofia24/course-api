package com.coursesystem.app.services;

import java.util.List;

import com.coursesystem.app.enums.ECourseStatus;
import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Course;
import com.coursesystem.app.models.Organization;
import com.coursesystem.app.models.Student;
import com.coursesystem.app.payload.forms.CourseForm;

public interface CourseService {
    
    public Course findById(Long id) throws nonExistentIdException;

    public Course chargeFormData(CourseForm courseForm) throws nonExistentIdException;

    public Course changeCourseStatus(Long id, String status) throws nonExistentIdException;

    public List<Course> findByStudentAndInProgressStatus(Student student, ECourseStatus courseStatus);
    
    public List<Course> findByStudentAndFinalizedStatus(Student student, ECourseStatus courseStatus);

    public List<Course> findByCategoryAndOrganization(String category, Organization org);

    public Course update(CourseForm courseForm, Long id) throws nonExistentIdException;
}
