package org.lavertis.tasktrackerapi.dto.tag;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TagResponse {
    private UUID id;
    private String name;
}
