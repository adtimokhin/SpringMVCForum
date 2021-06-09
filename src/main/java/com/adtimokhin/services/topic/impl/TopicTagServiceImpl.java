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
    //Repositories
    @Autowired
    private TopicTagRepository repository;

    @Override
    public void addTag(String tag) {
        TopicTag topicTag = new TopicTag();
        topicTag.setTagName(tag.toUpperCase());
        repository.save(topicTag);
    }

    @Override
    public void deleteTag(TopicTag tag) {
        repository.delete(tag);
    }

    @Override
    public void deleteTag(String tag) {
        repository.delete(getTagByName(tag));
    }

    @Override
    public TopicTag getTagByName(String name) {
        return repository.findByTagName(name);
    }

    @Override
    public TopicTag getTagById(long id) {
        return repository.findById(id);
    }

    @Override
    public Set<TopicTag> getTagsByNames(String... names) {
        List<String> listNames = Arrays.stream(names).map(String::toUpperCase).collect(Collectors.toList());
        return new HashSet<>(repository.findAllByTagNameIn(listNames));
    }

    @Override
    public boolean tagExists(String tagName) {
        return repository.existsByTagName(tagName.toUpperCase());
    }
}
