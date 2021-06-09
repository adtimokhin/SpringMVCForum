package com.adtimokhin.services.comment.impl;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.comment.CommentTag;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.comment.CommentRepository;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.topic.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adtimokhin
 * 24.04.2021
 **/

@Component
public class CommentServiceImpl implements CommentService {

    //Repositories
    @Autowired
    private CommentRepository repository;


    //Services
    @Autowired
    private CommentTagsServiceImpl tagsService;

    @Autowired
    private TopicService topicService;


    @Autowired
    private ContextProvider contextProvider;



    @Override
    public void addComment(String text, long topicId, List<Long> tagIds) {
        Topic topic = topicService.getTopic(topicId);
        if (topic == null) { //TODO: add this sort of checks in all services
            //TODO: when logging will be added, this kind of issue should be logged.
            return;
        }
        if(topic.isClosed()){
            return;
        }
        Comment comment = new Comment();
        comment.setUser(contextProvider.getUser());
        comment.setText(text);
        comment.setTopic(topic);
        comment.setTotalLikes(0);
        setTags(comment, tagIds);

    }

    @Override
    public void deleteComment(Comment comment) {
        repository.delete(comment);
    }

    @Override
    public List<Comment> getAllCommentsByTopic(long topicId) {
        Topic topic = topicService.getTopic(topicId);
        if (topic == null) {
            //TODO: when logging will be added, this kind of issue should be logged.
            return null;
        }
        return repository.getAllByTopicOrderByTotalLikesDesc(topic);

    }

    @Override
    public Comment getCommentById(long id) {
        return repository.getById(id);
    }

    @Override
    public void incrementTotalLikes(Comment comment) {
        comment.setTotalLikes(comment.getTotalLikes() + 1);
        repository.save(comment);
    }

    @Override
    public void decrementTotalLikes(Comment comment) {
        comment.setTotalLikes(comment.getTotalLikes() - 1);
        repository.save(comment);
    }

    @Override
    public void setTags(Comment comment, List<Long> tagIds) {
        comment.setTags(tagsService.getCommentTagsByIds(tagIds));
        repository.save(comment);
    }

    @Override
    public List<String> getTagNames(Comment comment) {
        return comment.getTags().stream().map(CommentTag::getTagName).collect(Collectors.toList());
    }

    @Override
    public List<Comment> getFlaggedComment(long topicId) {
        return null;
    }

    @Override
    public List<Comment> getFlaggedComment(List<Comment> comments) {
        List<Comment> c = new ArrayList<>();
        for (Comment comment:
             comments) {
            if(comment.isFlagged()){
                c.add(comment);
            }
        }
        if (c.size() == 0){return null;}
        return c;
    }

    @Override
    public void flagComment(long id, User user) {
        Comment comment = getCommentById(id);
        if(comment == null){
            return;
        }
        if(comment.getTopic().getUser().equals(user)){
            comment.setFlagged(true);
            repository.save(comment);
        }
    }


}
