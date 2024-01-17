package org.lavertis.tasktrackerapi.converter;

import org.lavertis.tasktrackerapi.dto.tag.TagResponse;
import org.lavertis.tasktrackerapi.entity.Tag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagResponse mapTagToTagResponse(Tag tag);
    List<TagResponse> mapTagsToTagResponses(List<Tag> tags);
}
