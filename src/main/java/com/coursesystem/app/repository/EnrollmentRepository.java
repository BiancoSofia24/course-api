package com.coursesystem.app.repository;

import com.coursesystem.app.models.Enrollment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends CrudRepository<Enrollment, Long> {
    
}
