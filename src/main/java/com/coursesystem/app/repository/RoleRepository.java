package com.coursesystem.app.repository;

import java.util.Optional;

import com.coursesystem.app.enums.EUserRole;
import com.coursesystem.app.models.Role;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	Optional<Role> findByUserRole(EUserRole userRole);
}
