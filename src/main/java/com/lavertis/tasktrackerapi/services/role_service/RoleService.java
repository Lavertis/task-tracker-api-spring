package com.lavertis.tasktrackerapi.services.role_service;

import com.lavertis.tasktrackerapi.entities.Role;
import com.lavertis.tasktrackerapi.repositories.IRoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService implements IRoleService {
    private final IRoleRepository roleRepository;

    public RoleService(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }
}
