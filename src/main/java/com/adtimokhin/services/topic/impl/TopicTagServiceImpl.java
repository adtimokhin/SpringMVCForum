package com.adtimokhin.services.topic.impl;

import com.adtimokhin.models.topic.TopicTag;
import com.adtimokhin.repositories.topic.TopicTagRepository;
import com.adtimokhin.services.topic.TopicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author adtimokhin
 * 08.05.2021
 **/

@Component
public class TopicTagServiceImpl implements TopicTagService {

    @Autowired
    private TopicTagRepository tagRepository;

    @Override
    public void addTag(String tag) {
        TopicTag topicTag = new TopicTag();
        topicTag.setTagName(tag.toUpperCase());
        tagRepository.save(topicTag);
    }

    @Override
    public void deleteTag(TopicTag tag) {
        tagRepository.delete(tag);
    }

    @Override
    public void deleteTag(String tag) {
        tagRepository.delete(getTagByName(tag));
    }

    @Override
    public TopicTag getTagByName(String name) {
        return tagRepository.findByTagName(name);
    }

    @Override
    public TopicTag getTagById(long id) {
        return tagRepository.findById(id);
    }

    @Override
    public Set<TopicTag> getTagsByNames(String... names) {
        List<String> listNames = Arrays.stream(names).map(String::toUpperCase).collect(Collectors.toList());
        return new HashSet<>(tagRepository.findAllByTagNameIn(listNames));
    }

    @Override
    public boolean tagExists(String tagName) {
        return tagRepository.existsByTagName(tagName.toUpperCase());
    }
}
