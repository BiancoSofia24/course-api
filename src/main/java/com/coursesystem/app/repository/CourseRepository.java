package com.coursesystem.app.repository;

import com.coursesystem.app.models.Course;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    
}
