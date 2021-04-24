package com.coursesystem.app.services;

import com.coursesystem.app.exceptions.nonExistentIdException;
import com.coursesystem.app.models.Course;
import com.coursesystem.app.payload.forms.CourseForm;

public interface CourseService {
    
    public Course findById(Long id) throws nonExistentIdException;

    public Course chargeFormData(CourseForm courseForm) throws nonExistentIdException;

    public Course changeCourseStatus(Long id, String status) throws nonExistentIdException;

    public Course update(CourseForm courseForm, Long id) throws nonExistentIdException;

    public Iterable<Course> findByCategoryAndOrganization(String category, Long orgId) throws nonExistentIdException;

}
