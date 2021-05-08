package com.adtimokhin.services;

import com.adtimokhin.models.Topic;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adtimokhin
 * 14.04.2021
 **/
@Service
public interface TopicService {

    void addTopic(Topic topic);

    List<Topic> getAllTopics();

    @Nullable Topic getTopic(long id);

    List<Topic> getAllTopicsForStudents();

    boolean isUserAllowedOntoTopic(Topic topic);
}
