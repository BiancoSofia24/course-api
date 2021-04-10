package com.coursesystem.app.repository;

import com.coursesystem.app.models.Student;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    
}
