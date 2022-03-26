package com.lavertis.tasktrackerapi.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String description;

    private Date finishDate;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<User> taskOwners;

    public Task(String name, String description, Date finishDate, List<User> taskOwners) {
        this.name = name;
        this.description = description;
        this.finishDate = finishDate;
        this.taskOwners = taskOwners;
    }
}
