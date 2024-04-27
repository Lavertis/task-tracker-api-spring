package org.lavertis.tasktrackerapi.service.tag_service;

import org.lavertis.tasktrackerapi.dto.tag.TagResponse;

import java.util.List;

public interface ITagService {
    List<TagResponse> getTags();
}
