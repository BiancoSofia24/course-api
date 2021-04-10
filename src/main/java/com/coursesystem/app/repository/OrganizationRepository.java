package com.coursesystem.app.repository;

import com.coursesystem.app.models.Organization;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
    
}
