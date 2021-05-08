package com.adtimokhin.services.topic;

import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.topic.TopicTag;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author adtimokhin
 * 14.04.2021
 **/
@Service
public interface TopicService {

    void addTopic(Topic topic);

    void addTopic(Topic topic, String... tags);

    void addTopic(Topic topic, Set<TopicTag> tags);

    List<Topic> getAllTopics();

    @Nullable
    Topic getTopic(long id);

    List<Topic> getAllTopicsForStudents();

    void setTags(Topic topic, String... tags);

    boolean isUserAllowedOntoTopic(Topic topic);
}
