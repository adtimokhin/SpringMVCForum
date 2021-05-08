package com.adtimokhin.services.like.impl;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.like.Like;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.like.LikeRepository;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.like.LikeService;
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
    @Autowired
    private LikeRepository repository;

    @Autowired
    private CommentService commentService;

    @Override
    public void addLike(User user, Comment comment) {
        Like like = new Like();
        like.setUser(user);
        like.setComment(comment);
        if(!likeExists(like)){
            repository.save(like);
            commentService.incrementTotalLikes(comment);
        }else {
            //todo: throw some kind of an error
        }
    }

    @Override
    public void deleteLike(User user, Comment comment) {
        repository.deleteByUserAndComment(user , comment);
        commentService.decrementTotalLikes(comment);
    }

    @Override
    public List<Long> getAllLikedCommentIdsByUser(User user, List<Comment> comments) {
        List<Long> comment_ids = comments.stream().map((Comment::getId)).collect(Collectors.toList());
        List<Like> likes =  repository.getAllByUserIdAndCommentIdIn(user.getId() , comment_ids);
        return  likes.stream().map(Like::getComment).map(Comment::getId).collect(Collectors.toList());
    }

     private boolean likeExists(Like like){
        return repository.findByUserAndComment(like.getUser() , like.getComment()) != null;
    }
}
