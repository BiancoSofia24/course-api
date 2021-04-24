package com.coursesystem.app.repository;

import com.coursesystem.app.enums.ECourseStatus;
import com.coursesystem.app.models.Course;
import com.coursesystem.app.models.Organization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    
    public Iterable<Course> findByCategory(String category);

    public Iterable<Course> findByOrg(Organization org);
    
    public Iterable<Course> findByCourseStatus(ECourseStatus courseStatus);

}
