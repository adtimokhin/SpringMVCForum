package com.adtimokhin.services.comment.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.comment.CommentTag;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.comment.CommentRepository;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.topic.TopicService;
import org.apache.log4j.Logger;
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

    private static final Logger logger = Logger.getLogger("file");


    @Override
    public void addComment(String text, long topicId, List<Long> tagIds) {
        Topic topic = topicService.getTopic(topicId);
        if (topic == null) {
            logger.info("No topic with id "+ topicId + " was found");
            return;
        }
        if (topic.isClosed()) {
            return;
        }
        User user = contextProvider.getUser();
        Comment comment = new Comment();
        comment.setUser(user);
        comment.setText(text);
        comment.setTopic(topic);
        comment.setTotalLikes(0);
        if (user.getRoles().contains(Role.ROLE_ORGANIZATION_MEMBER)) {
            if (!tagIds.contains((long) 4)) {
                tagIds.add((long) 4);
            }
        }
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
            logger.info("No topic with id "+ topicId + " was found");
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
        if(comment == null){
            logger.info("Tried to add a like to a null comment");
            return;
        }
        comment.setTotalLikes(comment.getTotalLikes() + 1);
        repository.save(comment);
    }

    @Override
    public void decrementTotalLikes(Comment comment) {
        if(comment == null){
            logger.info("Tried to remove a like to a null comment");
            return;
        }
        comment.setTotalLikes(comment.getTotalLikes() - 1);
        repository.save(comment);
    }

    @Override
    public void setTags(Comment comment, List<Long> tagIds) {
        if(comment == null){
            logger.info("Tried to set tags to a null comment");
            return;
        }
        if (tagIds.isEmpty()) {
            return;
        }
        comment.setTags(tagsService.getCommentTagsByIds(tagIds));
        repository.save(comment);
    }

    @Override
    public List<String> getTagNames(Comment comment) {
        if(comment == null){
            logger.info("Tried to get tags from a null comment");
            return null;
        }
        return comment.getTags().stream().map(CommentTag::getTagName).collect(Collectors.toList());
    }

    @Override
    public List<Comment> getFlaggedComment(long topicId) {
        return null;
    }

    @Override
    public List<Comment> getFlaggedComment(List<Comment> comments) {
        List<Comment> c = new ArrayList<>();
        for (Comment comment :
                comments) {
            if (comment.isFlagged()) {
                c.add(comment);
            }
        }
        if (c.size() == 0) {
            return null;
        }
        return c;
    }

    @Override
    public void flagComment(long id, User user) {
        if (user == null){
            logger.info("Tried to flag a comment with id " + id + " by a null user");
            return;
        }
        Comment comment = getCommentById(id);
        if (comment == null) {
            logger.info("Tried to flag a null comment with id " + id + " by a user with id " + user.getId());
            return;
        }
        if (comment.getTopic().getUser().equals(user)) {
            comment.setFlagged(true);
            repository.save(comment);
        }else {
            logger.info("Tried to flag a  comment with id " + id + " by a user with id " + user.getId() + " that didn't create the comment.");
        }
    }


}
