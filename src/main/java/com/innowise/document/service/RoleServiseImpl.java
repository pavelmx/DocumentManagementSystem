package com.innowise.document.service;

import com.innowise.document.entity.Role;
import com.innowise.document.entity.RoleName;
import com.innowise.document.repository.RoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiseImpl implements RoleService {

    @Autowired
    RoleRepo roleRepo;

    @Override
    public Role findByName(RoleName name){
        return roleRepo.findByName(name)
                .orElseThrow(() -> new RuntimeException("Role with name: '" + name + "' not found."));
    }

    @Override
    public List<Role> getAll(){
        List<Role> roleList = new ArrayList<>();
        roleRepo.findAll().forEach(roleList::add);
        return roleList;
    }

    @Override
    public Role getById(Long id){
        return roleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role with id: '" + id + "' not found."));
    }

    @Override
    public void deleteById(Long id){
        roleRepo.deleteById(id);
    }

    @Override
    public boolean existsById(Long id){
        return roleRepo.existsById(id);
    }
}
