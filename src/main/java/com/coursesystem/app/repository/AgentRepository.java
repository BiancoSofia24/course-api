package com.coursesystem.app.repository;

import com.coursesystem.app.models.Agent;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends CrudRepository<Agent, Long> {
    
}
