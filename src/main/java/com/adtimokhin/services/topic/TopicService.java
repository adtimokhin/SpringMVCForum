package com.adtimokhin.services.topic;

import com.adtimokhin.models.topic.Topic;
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
