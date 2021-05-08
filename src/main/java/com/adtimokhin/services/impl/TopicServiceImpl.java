package com.adtimokhin.services.impl;

import com.adtimokhin.models.Roles;
import com.adtimokhin.models.Topic;
import com.adtimokhin.repositories.TopicRepository;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author adtimokhin
 * 14.04.2021
 **/
@Component
public class TopicServiceImpl implements TopicService {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ContextProvider contextProvider;

    @Override
    public void addTopic(Topic topic) {
        topic.setUser(contextProvider.getUser());
        topicRepository.save(topic);
    }

    @Override
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @Override
    public Topic getTopic(long id) {
        Optional<Topic> topic = topicRepository.findById(id);
        return topic.orElse(null);
    }

    @Override
    public List<Topic> getAllTopicsForStudents() {
        Set<Roles> roles = Collections.singleton(Roles.ROLE_STUDENT);
        return topicRepository.getAllByUser_RolesIsContaining(roles);
    }


    /**
     * Checks that a user with role of "ROLE_STUDENT" is allowed onto the topic page or not.
     * Students are only allowed to the topics that are initialized by students as well.
     **/
    @Override
    public boolean isUserAllowedOntoTopic(Topic topic) { //Todo: maybe create a filter with this logic (when I will learn how they work)
        Set<Roles> creatorRole = topic.getUser().getRoles();
        return creatorRole.contains(Roles.ROLE_STUDENT);
    }

}
