package com.security.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.security.services.constants.RoleConstants;
import com.security.services.model.Role;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RoleConstants name);
}
