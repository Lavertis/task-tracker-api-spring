package org.lavertis.tasktrackerapi.service.task_service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.sqm.tree.SqmCopyContext;
import org.hibernate.query.sqm.tree.select.SqmSelectStatement;
import org.hibernate.query.sqm.tree.select.SqmSubQuery;
import org.lavertis.tasktrackerapi.dto.PagedResponse;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.TaskQuery;
import org.lavertis.tasktrackerapi.dto.task.TaskResponse;
import org.lavertis.tasktrackerapi.dto.task.UpdateTaskRequest;
import org.lavertis.tasktrackerapi.entity.Task;
import org.lavertis.tasktrackerapi.entity.User;
import org.lavertis.tasktrackerapi.repository.ITaskRepository;
import org.lavertis.tasktrackerapi.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class TaskService implements ITaskService {
    @Autowired
    private ITaskRepository taskRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow();
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .build();
    }

    @Override
    public PagedResponse<TaskResponse> getTasks(TaskQuery taskQuery, String username) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> task = cq.from(Task.class);

        Predicate titlePredicate = null;
        if (!taskQuery.getSearchTitle().isEmpty())
            titlePredicate = cb.like(cb.lower(task.get("title")), "%" + taskQuery.getSearchTitle().toLowerCase() + "%");

        Predicate completedPredicate = null;
        if (taskQuery.getHideCompleted() != null && taskQuery.getHideCompleted())
            completedPredicate = cb.equal(task.get("completed"), false);

        Predicate userPredicate = null;
        if (username != null)
            userPredicate = cb.equal(task.get("user").get("username"), username);

        var predicates = Stream.of(titlePredicate, completedPredicate, userPredicate)
                .filter(Objects::nonNull)
                .toArray(Predicate[]::new);
        cq.where(predicates);
        cq.orderBy(cb.asc(task.get("dueDate")));

        var countQuery = entityManager.createQuery(cq);
        var totalCount = countQuery.getResultList().size();

        TypedQuery<Task> tasksQuery;
        if (taskQuery.getRangeStart() != null && taskQuery.getRangeEnd() != null) {
            tasksQuery = entityManager.createQuery(cq)
                    .setFirstResult(taskQuery.getRangeStart())
                    .setMaxResults(taskQuery.getRangeEnd() - taskQuery.getRangeStart());
        } else {
            tasksQuery = entityManager.createQuery(cq);
        }

        List<Task> tasks = tasksQuery.getResultList();

        List<TaskResponse> taskResponses = tasks.stream().map(taskEntity -> TaskResponse.builder()
                .id(taskEntity.getId())
                .title(taskEntity.getTitle())
                .description(taskEntity.getDescription())
                .completed(taskEntity.getCompleted())
                .priority(taskEntity.getPriority())
                .dueDate(taskEntity.getDueDate())
                .userId(taskEntity.getUser().getId())
                .build()
        ).toList();
        PagedResponse<TaskResponse> response = new PagedResponse<>();
        response.setTotalCount((long)totalCount);
        response.setItems(taskResponses);
        return response;
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request, String username) {
        User user = userRepository.findByUsername(username);
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .completed(request.getCompleted())
                .priority(request.getPriority())
                .dueDate(request.getDueDate())
                .user(user)
                .build();
        task = taskRepository.save(task);
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .build();
    }

    @Override
    public TaskResponse updateTask(UUID id, UpdateTaskRequest request) {
        Task task = taskRepository.findById(id).orElseThrow();
        if (request.getTitle() != null)
            task.setTitle(request.getTitle());
        if (request.getDescription() != null)
            task.setDescription(request.getDescription());
        if (request.getCompleted() != null)
            task.setCompleted(request.getCompleted());
        if (request.getPriority() != null)
            task.setPriority(request.getPriority());
        if (request.getDueDate() != null)
            task.setDueDate(request.getDueDate());
        task = taskRepository.save(task);
        return TaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .completed(task.getCompleted())
                .priority(task.getPriority())
                .dueDate(task.getDueDate())
                .userId(task.getUser().getId())
                .build();
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }
}
