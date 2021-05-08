package com.adtimokhin.services.topic;

import com.adtimokhin.models.topic.TopicTag;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * @author adtimokhin
 * 08.05.2021
 **/

@Service
public interface TopicTagService {

    void addTag(String tag);

    @Transactional
    void deleteTag(TopicTag tag);

    @Transactional
    void deleteTag(String tag);

    TopicTag getTagByName(String name);

    TopicTag getTagById(long id);

    Set<TopicTag> getTagsByNames(String... names);

    boolean tagExists(String tagName);


}
