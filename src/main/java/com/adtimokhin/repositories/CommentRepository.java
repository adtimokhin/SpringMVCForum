package com.adtimokhin.repositories;

import com.adtimokhin.models.Comment;
import com.adtimokhin.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 24.04.2021
 **/
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> getAllByTopic(Topic topic);

    Comment getById(long id);

    List<Comment> getAllByTopicOrderByTotalLikesDesc(Topic topic);
}
