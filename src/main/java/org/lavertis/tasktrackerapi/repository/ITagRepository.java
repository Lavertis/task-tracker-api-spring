package org.lavertis.tasktrackerapi.repository;

import org.lavertis.tasktrackerapi.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITagRepository extends JpaRepository<Tag, UUID> {
}
