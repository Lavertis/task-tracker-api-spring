package org.lavertis.tasktrackerapi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Task> tasks;

    public Tag(UUID id, String name) {
        this.id = id;
        this.name = name;
    }
}
