package com.adtimokhin.services.topic.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.topic.TopicTag;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.topic.TopicRepository;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.topic.TopicService;
import com.adtimokhin.services.topic.TopicTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author adtimokhin
 * 14.04.2021
 **/
@Component
public class TopicServiceImpl implements TopicService {
    //Repositories
    @Autowired
    private TopicRepository repository;


    //Services
    @Autowired
    private TopicTagService topicTagService;



    @Autowired
    private ContextProvider contextProvider;

    @Override
    public void addTopic(Topic topic) {
        topic.setUser(contextProvider.getUser());
        repository.save(topic);
    }

    @Override
    public void addTopic(Topic topic, String... tags) {
        setTags(topic, tags);
        addTopic(topic);
    }

    @Override
    public void addTopic(Topic topic, Set<TopicTag> tags) {
        topic.setTags(tags);
        addTopic(topic);
    }

    @Override
    public void closeTopic(Topic topic) {
        topic.setClosed(true);
    }

    @Override
    public void closeTopic(long topicId, User user) {
        Topic topic = getTopic(topicId);
        if(topic == null){
            return;
        }
        if(isUserCreatedTopic(topic, user)){
            topic.setClosed(true);
            repository.save(topic);
        }
    }

    @Override
    public void openTopic(Topic topic) {
        topic.setClosed(false);
    }

    @Override
    public void openTopic(long topicId, User user) {
        Topic topic = getTopic(topicId);
        if(topic == null){
            return;
        }
        if(isUserCreatedTopic(topic, user)){
            topic.setClosed(false);
            repository.save(topic);
        }
    }

    @Override
    public List<Topic> getAllTopics() {
        return repository.findAll();
    }

    @Override
    public Topic getTopic(long id) {
        Optional<Topic> topic = repository.findById(id);
        return topic.orElse(null);
    }

    @Override
    public List<Topic> getAllTopicsForStudents() {
        Set<Role> roleStudent = Collections.singleton(Role.ROLE_STUDENT);
        Set<Role> roleMember = Collections.singleton(Role.ROLE_ORGANIZATION_MEMBER);
        List<Topic> topics = repository.getAllByUser_RolesIsContaining(roleStudent);
        topics.addAll(repository.getAllByUser_RolesIsContaining(roleMember));
        return topics;
    }

    @Override
    public void setTags(Topic topic, String... tags) {
        for (String tag : tags) {
            if (!topicTagService.tagExists(tag)) {
                topicTagService.addTag(tag);
            }
        }
        topic.setTags(topicTagService.getTagsByNames(tags));
    }


    /**
     * Checks that a user with role of "ROLE_STUDENT" is allowed onto the topic page or not.
     * Students are only allowed to the topics that are initialized by students as well.
     **/
    @Override
    public boolean isUserAllowedOntoTopic(Topic topic) { //Todo: maybe create a filter with this logic (when I will learn how they work)
        Set<Role> creatorRole = topic.getUser().getRoles();
        return creatorRole.contains(Role.ROLE_STUDENT);
    }

    @Override
    public boolean isUserAllowedOntoTopic(Topic topic, User user) {
        Set<Role> creatorRole = topic.getUser().getRoles();
        if (creatorRole.contains(Role.ROLE_PARENT)){
            Set<Role> roles = user.getRoles();
            if(roles.contains(Role.ROLE_PARENT) || roles.contains(Role.ROLE_ORGANIZATION_MEMBER)){
                return true;
            }
            return false;
        }
        return true;

    }

    @Override
    public boolean isUserCreatedTopic(Topic topic, User user) {
        return user.equals(topic.getUser());
    }

    @Override
    public boolean isUserCreatedTopic(long topicId, User user) {
        return repository.getById(topicId).getUser().equals(user);
    }

}
