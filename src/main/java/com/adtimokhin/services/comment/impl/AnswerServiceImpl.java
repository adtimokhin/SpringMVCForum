package com.adtimokhin.services.comment.impl;

import com.adtimokhin.models.comment.Answer;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.comment.AnswerRepository;
import com.adtimokhin.services.comment.AnswerService;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 10.06.2021
 **/

@Component
public class AnswerServiceImpl implements AnswerService {
    @Autowired
    private AnswerRepository repository;


    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;


    private static final Logger logger = Logger.getLogger("file");

    @Override
    public void addAnswer(String text, User user, long commentId) {
        if (user == null){
            logger.info("Null user tried to add an answer to a comment with id "+ commentId);
            return;
        }
        Comment comment = commentService.getCommentById(commentId);

        if (comment ==null){
            logger.info("User with id" + user.getId() +  "tried to add an answer to a null comment with id "+ commentId);
            return;
        }

        Answer answer = new Answer();

        answer.setText(text);
        answer.setUser(user);
        answer.setComment(comment);

        repository.save(answer);

    }

    @Override
    public List<Answer> getAllAnswersByCommentId(long commentId) {
        Comment comment = commentService.getCommentById(commentId);

        if (comment == null){
            logger.info("Tried to get answers to a null comment with id "+ commentId);
            return null;
        }

        return repository.getAllByComment(comment);
    }
}
