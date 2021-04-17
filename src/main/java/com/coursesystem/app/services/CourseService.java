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

    public Course chargeFormCourse(CourseForm courseForm, Course course) throws nonExistentIdException;

    public List<Course> findByCategory(String category);

    public List<Course> findByStudentAndInProgressStatus(Student student, ECourseStatus courseStatus);
    
    public List<Course> findByStudentAndFinalizedStatus(Student student, ECourseStatus courseStatus);

    public List<Course> findByCategoryAndOrganization(String category, Organization org);
}
