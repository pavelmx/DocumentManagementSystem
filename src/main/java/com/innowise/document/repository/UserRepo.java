package com.innowise.document.repository;

import com.innowise.document.entity.QUser;
import com.innowise.document.entity.Role;
import com.innowise.document.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepo extends CustomRepo<User, QUser, Long> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRoles(Set<Role> roles);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByActivationCode(String code);
}
