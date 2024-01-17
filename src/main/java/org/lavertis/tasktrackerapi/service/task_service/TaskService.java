package org.lavertis.tasktrackerapi.service.task_service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.converter.TaskMapper;
import org.lavertis.tasktrackerapi.dto.PagedResponse;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.task.TaskQuery;
import org.lavertis.tasktrackerapi.dto.task.TaskResponse;
import org.lavertis.tasktrackerapi.dto.task.UpdateTaskRequest;
import org.lavertis.tasktrackerapi.entity.Tag;
import org.lavertis.tasktrackerapi.entity.Task;
import org.lavertis.tasktrackerapi.entity.AppUser;
import org.lavertis.tasktrackerapi.repository.ITagRepository;
import org.lavertis.tasktrackerapi.repository.ITaskRepository;
import org.lavertis.tasktrackerapi.repository.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class TaskService implements ITaskService {
    private ITaskRepository taskRepository;
    private IUserRepository userRepository;
    private ITagRepository tagRepository;
    private EntityManager entityManager;
    private TaskMapper taskMapper;

    @Override
    public TaskResponse getTaskById(UUID id) {
        Task task = taskRepository.findById(id).orElseThrow();
        return taskMapper.mapTaskToTaskResponse(task);
    }

    @Override
    public PagedResponse<TaskResponse> getUserTasks(TaskQuery taskQuery, UUID userId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Task> cq = cb.createQuery(Task.class);
        Root<Task> task = cq.from(Task.class);

        Predicate titlePredicate = null;
        if (!taskQuery.getSearchTitle().isEmpty())
            titlePredicate = cb.like(cb.lower(task.get("title")), "%" + taskQuery.getSearchTitle().toLowerCase() + "%");

        Predicate completedPredicate = null;
        if (taskQuery.getHideCompleted() != null && taskQuery.getHideCompleted())
            completedPredicate = cb.equal(task.get("completed"), false);

        Predicate priorityPredicate = null;
        if (taskQuery.getPriority() != null)
            priorityPredicate = cb.equal(task.get("priority"), taskQuery.getPriority());

        Predicate userPredicate = null;
        if (userId != null)
            userPredicate = cb.equal(task.get("user").get("id"), userId);

        var predicates = Stream.of(titlePredicate, completedPredicate, userPredicate, priorityPredicate)
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

        List<TaskResponse> taskResponses = tasks.stream()
                .map(taskMapper::mapTaskToTaskResponse)
                .toList();
        PagedResponse<TaskResponse> response = new PagedResponse<>();
        response.setTotalCount((long) totalCount);
        response.setItems(taskResponses);
        return response;
    }

    @Override
    public TaskResponse createTask(CreateTaskRequest request, UUID userId) {
        AppUser user = userRepository.findById(userId).orElseThrow();
        List<Tag> tags = null;
        if (request.getTags() != null)
            tags = tagRepository.findAllById(request.getTags());

        Task task = taskMapper.mapCreateTaskRequestToTask(request);
        task.setUser(user);
        task.setTags(tags);
        task = taskRepository.save(task);

        return taskMapper.mapTaskToTaskResponse(task);
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
        if (request.getTags() != null) {
            List<Tag> tags = tagRepository.findAllById(request.getTags());
            task.setTags(tags);
        }

        task = taskRepository.save(task);
        return taskMapper.mapTaskToTaskResponse(task);
    }

    @Override
    public void deleteTask(UUID id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Boolean isUserTaskOwner(UUID taskId, UUID userId) {
        Task task = taskRepository.findById(taskId).orElseThrow();
        return task.getUserId().equals(userId);
    }
}
