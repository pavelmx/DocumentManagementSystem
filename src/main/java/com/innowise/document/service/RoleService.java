package com.innowise.document.service;

import com.innowise.document.entity.Role;
import com.innowise.document.entity.RoleName;

public interface RoleService extends RestService<Role> {

    Role findByName(RoleName name);
}
