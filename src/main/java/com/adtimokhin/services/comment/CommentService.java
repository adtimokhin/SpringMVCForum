package com.adtimokhin.services.comment;

import com.adtimokhin.models.comment.Comment;
import org.springframework.lang.Nullable;
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

    List<Comment> getFlagged(long topicId);

    @Nullable
    List<Comment> getFlagged(List<Comment> comments);
}
