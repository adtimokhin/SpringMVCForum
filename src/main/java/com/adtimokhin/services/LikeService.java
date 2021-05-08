package com.adtimokhin.services;

import com.adtimokhin.models.Comment;
import com.adtimokhin.models.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 01.05.2021
 **/

@Service
public interface LikeService {

    void addLike(User user, Comment comment);

    @Transactional
    void deleteLike(User user, Comment comment);

    List<Long> getAllLikedCommentIdsByUser(User user, List<Comment> comments);
}
