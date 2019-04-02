package com.innowise.document.repository;

import com.innowise.document.entity.QRole;
import com.innowise.document.entity.Role;
import com.innowise.document.entity.RoleName;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends CustomRepo<Role, QRole, Long>{

    Optional<Role> findByName(RoleName name);
}
