package com.adtimokhin.services.comment.impl;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.comment.CommentTag;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.repositories.comment.CommentRepository;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adtimokhin
 * 24.04.2021
 **/

@Component
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentTagsServiceImpl tagsService;

    @Autowired
    private ContextProvider contextProvider;

    @Autowired
    private TopicService topicService;


    @Override
    public void addComment(String text, long topicId, List<Long> tagIds) {
        Comment comment = new Comment();
        comment.setUser(contextProvider.getUser());
        comment.setText(text);
        Topic topic = topicService.getTopic(topicId);
        if (topic == null){ //TODO: add this sort of checks in all services
            //TODO: when logging will be added, this kind of issue should be logged.
            return;
        }
        comment.setTopic(topic);
        comment.setTotalLikes(0);
        setTags(comment , tagIds);
//        commentRepository.save(comment);

    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public List<Comment> getAllCommentsByTopic(long topicId) {
        Topic topic = topicService.getTopic(topicId);
        if(topic == null){
            //TODO: when logging will be added, this kind of issue should be logged.
            return null;
        }
        return commentRepository.getAllByTopicOrderByTotalLikesDesc(topic);

    }

    @Override
    public Comment getCommentById(long id) {
        return commentRepository.getById(id);
    }

    @Override
    public void incrementTotalLikes(Comment comment) {
        comment.setTotalLikes(comment.getTotalLikes() + 1);
        commentRepository.save(comment);
    }

    @Override
    public void decrementTotalLikes(Comment comment) {
        comment.setTotalLikes(comment.getTotalLikes() - 1);
        commentRepository.save(comment);
    }

    @Override
    public void setTags(Comment comment, List<Long> tagIds) {
        comment.setTags(tagsService.getCommentTagsByIds(tagIds));
        commentRepository.save(comment);
    }

    @Override
    public List<String> getTagNames(Comment comment) {
        return comment.getTags().stream().map(CommentTag::getTagName).collect(Collectors.toList());
    }


}
