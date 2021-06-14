package com.adtimokhin.repositories.comment;

import com.adtimokhin.models.comment.Answer;
import com.adtimokhin.models.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 10.06.2021
 **/

@Repository
public interface AnswerRepository extends JpaRepository<Answer , Long> {

    List<Answer> getAllByComment(Comment comment);

    Answer findById(long id);

}
