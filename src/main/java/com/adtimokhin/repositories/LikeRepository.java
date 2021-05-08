package com.adtimokhin.repositories;

import com.adtimokhin.models.Comment;
import com.adtimokhin.models.Like;
import com.adtimokhin.models.User;
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
