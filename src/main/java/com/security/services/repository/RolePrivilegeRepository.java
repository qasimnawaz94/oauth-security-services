package com.security.services.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.security.services.model.RolePrivilege;


@Repository
public interface RolePrivilegeRepository extends JpaRepository<RolePrivilege, Long> {

}
