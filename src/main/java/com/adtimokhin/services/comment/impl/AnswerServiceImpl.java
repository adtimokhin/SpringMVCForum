package com.adtimokhin.services.comment.impl;

import com.adtimokhin.models.comment.Answer;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.comment.AnswerRepository;
import com.adtimokhin.services.comment.AnswerService;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.user.UserService;
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

    @Override
    public void addAnswer(String text, User user, long commentId) {
        Answer answer = new Answer();

        answer.setText(text);
        answer.setUser(user);
        answer.setComment(commentService.getCommentById(commentId));

        repository.save(answer);

    }

    @Override
    public List<Answer> getAllAnswersByCommentId(long commentId) {
        return repository.getAllByComment(commentService.getCommentById(commentId));
    }
}
