package com.adtimokhin.services.comment;

import com.adtimokhin.models.comment.Answer;
import com.adtimokhin.models.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adtimokhin
 * 10.06.2021
 **/

@Service
public interface AnswerService {

    void addAnswer(String text, User user, long commentId);

    List<Answer> getAllAnswersByCommentId(long commentId);

}
