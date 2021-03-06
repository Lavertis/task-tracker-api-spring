package com.lavertis.tasktrackerapi.repositories;

import com.lavertis.tasktrackerapi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
