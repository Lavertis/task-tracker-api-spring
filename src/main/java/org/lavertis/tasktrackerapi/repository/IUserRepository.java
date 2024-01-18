package org.lavertis.tasktrackerapi.repository;

import org.lavertis.tasktrackerapi.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}
