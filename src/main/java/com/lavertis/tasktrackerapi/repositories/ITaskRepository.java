package com.lavertis.tasktrackerapi.repositories;

import com.lavertis.tasktrackerapi.entities.Task;
import com.lavertis.tasktrackerapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ITaskRepository extends JpaRepository<Task, Long> {
    List<Task> findTasksByTaskOwnersContaining(User taskOwner);

    boolean existsTaskByIdAndTaskOwnersContaining(Long id, User taskOwner);
}
