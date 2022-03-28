package com.lavertis.tasktrackerapi.services.role_service;

import com.lavertis.tasktrackerapi.entities.Role;

public interface IRoleService {
    Role getRoleByName(String name);
}
