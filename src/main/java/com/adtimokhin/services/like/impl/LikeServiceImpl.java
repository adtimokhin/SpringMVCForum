package com.adtimokhin.services.like.impl;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.like.Like;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.like.LikeRepository;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.like.LikeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author adtimokhin
 * 01.05.2021
 **/

@Component
public class LikeServiceImpl implements LikeService {

    //Repositories
    @Autowired
    private LikeRepository repository;


    //Services
    @Autowired
    private CommentService commentService;


    private static final Logger logger = Logger.getLogger("file");

    @Override
    public void addLike(User user, Comment comment) {
        if (user == null) {
            logger.info("Tried to add a like by a null user.");
            return;
        }

        if (comment == null) {
            logger.info("Tried to add a like by a user with id " + user.getId() + " to a null comment.");
            return;
        }

        if (comment.getTopic().isClosed()) {
            return;
        }

        Like like = new Like();
        like.setUser(user);
        like.setComment(comment);
        if (!likeExists(like)) {
            repository.save(like);
            commentService.incrementTotalLikes(comment);
        } else {
            logger.info("Tried to add a like by a user with id " + user.getId() + " to a comment with id " + comment.getId() + ", though already had.");
        }
    }

    @Override
    public void deleteLike(User user, Comment comment) {

        if (user == null) {
            logger.info("Tried to delete a like by a null user");
            return;
        }

        if (comment == null) {
            logger.info("Tried to delete a like by a user with id " + user.getId() + " from a null comment");
            return;
        }

        if (comment.getTopic().isClosed()) {
            return;
        }
        repository.deleteByUserAndComment(user, comment);
        commentService.decrementTotalLikes(comment);
    }

    @Override
    public List<Long> getAllLikedCommentIdsByUser(User user, List<Comment> comments) {
        if (user == null) {
            logger.info("Tried to get all likes for a range of comments by a null user");
            return null;
        }
        List<Long> comment_ids = comments.stream().map((Comment::getId)).collect(Collectors.toList());
        List<Like> likes = repository.getAllByUserIdAndCommentIdIn(user.getId(), comment_ids);
        return likes.stream().map(Like::getComment).map(Comment::getId).collect(Collectors.toList());
    }

    private boolean likeExists(Like like) {
        if (like == null) {
            return false;
        }
        return repository.findByUserAndComment(like.getUser(), like.getComment()) != null;
    }
}
