package org.lavertis.tasktrackerapi.repository;

import org.lavertis.tasktrackerapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByUserUsername(String username);
}
