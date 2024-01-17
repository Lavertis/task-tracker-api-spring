package org.lavertis.tasktrackerapi.repository;

import org.lavertis.tasktrackerapi.entity.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IUserRepository extends CrudRepository<AppUser, UUID> {
    AppUser findByUsername(String username);
}
