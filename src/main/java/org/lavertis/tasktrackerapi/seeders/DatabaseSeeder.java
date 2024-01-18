package org.lavertis.tasktrackerapi.seeders;

import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.dto.task.CreateTaskRequest;
import org.lavertis.tasktrackerapi.dto.user.CreateUserRequest;
import org.lavertis.tasktrackerapi.entity.AppUser;
import org.lavertis.tasktrackerapi.entity.Tag;
import org.lavertis.tasktrackerapi.repository.ITagRepository;
import org.lavertis.tasktrackerapi.repository.ITaskRepository;
import org.lavertis.tasktrackerapi.repository.IUserRepository;
import org.lavertis.tasktrackerapi.service.task_service.ITaskService;
import org.lavertis.tasktrackerapi.service.user_service.IUserService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final ITagRepository tagRepository;
    private final IUserRepository userRepository;
    private final IUserService userService;
    private final ITaskRepository taskRepository;
    private final ITaskService taskService;
    private final String USER_EMAIL = "user@gmail.com";

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void run(ApplicationArguments args) {
        seedTags();
        seedUser();
        seedUserTasks();
    }

    private void seedTags() {
        if (tagRepository.count() > 0) {
            return;
        }
        List<Tag> tags = new ArrayList<>();
        String[] taskRelatedTagNames = {"Research", "Design", "Development", "Testing", "Review", "Documentation", "Refactoring", "BugFixing", "Maintenance", "Deployment"};
        for (int i = 1; i <= taskRelatedTagNames.length; i++) {
            Tag tag = new Tag(UUID.randomUUID(), taskRelatedTagNames[i - 1]);
            tags.add(tag);
        }
        tagRepository.saveAll(tags);
    }

    private void seedUser() {
        if (userService.isEmailExist(USER_EMAIL)) {
            return;
        }

        CreateUserRequest request = CreateUserRequest
                .builder()
                .email(USER_EMAIL)
                .password("password")
                .firstName("John")
                .lastName("Doe")
                .build();
        userService.createUser(request);
    }

    private void seedUserTasks() {
        if (taskRepository.count() > 0) {
            return;
        }
        AppUser user = userService.getUserByUsername(USER_EMAIL);
        List<Tag> tags = tagRepository.findAll();
        for (int i = 1; i <= 100; i++) {
            CreateTaskRequest request = CreateTaskRequest
                    .builder()
                    .title("Task " + i)
                    .description("Task " + i + " description")
                    .completed(i % 3 == 0)
                    .priority(i % 4)
                    .dueDate(new Date(System.currentTimeMillis() + (-10L + i) * 24 * 60 * 60 * 1000))
                    .tags(List.of(
                            tags.get(i % tags.size()).getId(),
                            tags.get((i + 1) % tags.size()).getId(),
                            tags.get((i + 2) % tags.size()).getId()
                    ))
                    .build();
            taskService.createTask(request, user.getId());
        }
    }
}
