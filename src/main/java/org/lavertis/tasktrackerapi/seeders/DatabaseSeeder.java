package org.lavertis.tasktrackerapi.seeders;

import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.entity.Tag;
import org.lavertis.tasktrackerapi.repository.ITagRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class DatabaseSeeder implements ApplicationRunner {

    private final ITagRepository tagRepository;

    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void run(ApplicationArguments args) {
        seedTags();
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
}
