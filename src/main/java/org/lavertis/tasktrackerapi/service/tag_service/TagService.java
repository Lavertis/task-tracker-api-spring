package org.lavertis.tasktrackerapi.service.tag_service;

import lombok.AllArgsConstructor;
import org.lavertis.tasktrackerapi.converter.TagMapper;
import org.lavertis.tasktrackerapi.dto.tag.TagResponse;
import org.lavertis.tasktrackerapi.entity.Tag;
import org.lavertis.tasktrackerapi.repository.ITagRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService implements ITagService {
    private ITagRepository tagRepository;
    private TagMapper tagMapper;

    @Override
    public List<TagResponse> getTags() {
        List<Tag> tags = tagRepository.findAll();
        return tags.stream()
                .map(tagMapper::mapTagToTagResponse)
                .toList();
    }
}
