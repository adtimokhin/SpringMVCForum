package com.adtimokhin.repositories.topic;

import com.adtimokhin.models.topic.TopicTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 08.05.2021
 **/

@Repository
public interface TopicTagRepository extends JpaRepository<TopicTag , Long> {

    TopicTag findById(long id);

    TopicTag findByTagName(String name);

    List<TopicTag> findAllByTagNameIn(List<String> names);

    boolean existsByTagName(String name);


}
