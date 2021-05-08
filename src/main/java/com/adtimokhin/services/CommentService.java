package com.adtimokhin.services;

import com.adtimokhin.models.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 24.04.2021
 **/
@Service
public interface CommentService {

    void addComment(String text, long topicId, List<Long> tagIds);

    @Transactional
    void deleteComment(Comment comment);

    List<Comment> getAllCommentsByTopic(long topicId);

    Comment getCommentById(long id);

    void incrementTotalLikes(Comment comment);

    void decrementTotalLikes(Comment comment);

    void setTags(Comment comment, List<Long> tagIds);

    List<String> getTagNames(Comment comment);
}
