package com.coursesystem.app.repository;

//import java.util.Optional;

//import com.coursesystem.app.enums.EUserRole;
import com.coursesystem.app.models.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	//public Optional<Role> findByName(EUserRole userRole);
    //ERROR
}
