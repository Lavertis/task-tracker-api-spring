package org.lavertis.tasktrackerapi.repository;

import org.lavertis.tasktrackerapi.entity.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends CrudRepository<Task, UUID> {
    List<Task> findAllByUserUsername(String username);
}
