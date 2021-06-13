package com.adtimokhin.services.topic.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.topic.TopicTag;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.topic.TopicRepository;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.topic.TopicService;
import com.adtimokhin.services.topic.TopicTagService;
import org.apache.log4j.Logger;
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


    private static final Logger logger = Logger.getLogger("file");

    @Override
    public void addTopic(Topic topic) {
        if (topic ==null){
            logger.info("tried to add a null topic");
            return;
        }
        topic.setUser(contextProvider.getUser());
        repository.save(topic);
    }

    @Override
    public void addTopic(Topic topic, String... tags) {
        if (topic == null){
            logger.info("Tried to add a null topic");
            return;
        }
        setTags(topic, tags);
        addTopic(topic);
    }

    @Override
    public void addTopic(Topic topic, Set<TopicTag> tags) {
        if (topic == null){
            logger.info("Tried to add a null topic");
            return;
        }
        topic.setTags(tags);
        addTopic(topic);
    }

    @Override
    public void closeTopic(Topic topic) {
        if (topic == null){
            logger.info("Tried to close a null topic");
            return;
        }
        topic.setClosed(true);
    }

    @Override
    public void closeTopic(long topicId, User user) {
        Topic topic = getTopic(topicId);
        if(topic == null){
            logger.info("Tried to close a null topic");
            return;
        }
        if (user == null){
            logger.info("Tried to close a topic with id " + topicId + " by a null user");
            return;
        }
        if(isUserCreatedTopic(topic, user)){
            topic.setClosed(true);
            repository.save(topic);
        }else {
            logger.info("User with id " + user.getId() + " tried to close a topic with id "+ topicId + " thought that user didn't create that topic");
        }
    }

    @Override
    public void openTopic(Topic topic) {
        if (topic == null){
            logger.info("Tried to open a null topic");
            return;
        }
        topic.setClosed(false);
    }

    @Override
    public void openTopic(long topicId, User user) {
        Topic topic = getTopic(topicId);
        if(topic == null){
            logger.info("Tried to open a null topic");
            return;
        }
        if (user == null){
            logger.info("Tried to open a topic with id " + topicId + " by a null user");
            return;
        }
        if(isUserCreatedTopic(topic, user)){
            topic.setClosed(false);
            repository.save(topic);
        }else {
            logger.info("User with id " + user.getId() + " tried to open a topic with id "+ topicId + " thought that user didn't create that topic");
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
    public Topic getTopic(String topic) {
        Topic topic1 = repository.getByTopic(topic);
        return topic1;
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
        if(topic == null){
            logger.info("Tried to set tags for a null topic");
            return;
        }
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
        if (topic == null){
            logger.info("Tried to enter a null topic");
            return false;
        }
        Set<Role> creatorRole = topic.getUser().getRoles();
        return creatorRole.contains(Role.ROLE_STUDENT);
    }

    @Override
    public boolean isUserAllowedOntoTopic(Topic topic, User user) {
        if (topic == null){
            logger.info("Tried to enter a null topic");
            return false;
        }
        if (user == null){
            logger.info("A null user tried to enter a topic with id "+ topic.getId());
            return false;
        }
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
        if (topic == null){
            logger.info("Tried to enter a null topic");
            return false;
        }
        if (user == null){
            logger.info("A null user tried to enter a topic with id "+ topic.getId());
            return false;
        }
        return user.equals(topic.getUser());
    }

    @Override
    public boolean isUserCreatedTopic(long topicId, User user) {
        Topic topic = repository.getById(topicId);
        if (topic == null){
            logger.info("Tried to enter a null topic");
            return false;
        }
        if (user == null){
            logger.info("A null user tried to enter a topic with id "+ topic.getId());
            return false;
        }
        return topic.getUser().equals(user);
    }

}
