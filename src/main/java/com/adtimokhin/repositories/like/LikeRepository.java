package com.adtimokhin.repositories.like;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.like.Like;
import com.adtimokhin.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 01.05.2021
 **/
@Repository
public interface LikeRepository extends JpaRepository<Like , Long> {
    List<Like> getAllByUserIdAndCommentIdIn(long user_id, List<Long> comment_ids);

    Like findByUserAndComment(User user , Comment comment);

    void deleteByUserAndComment(User user , Comment comment);
}
