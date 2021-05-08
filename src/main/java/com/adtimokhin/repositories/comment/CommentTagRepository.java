package com.adtimokhin.repositories.comment;

import com.adtimokhin.models.comment.CommentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 03.05.2021
 **/
@Repository
public interface CommentTagRepository extends JpaRepository<CommentTag, Long> {

    CommentTag getByTagName(String tagName);

    CommentTag getById(long id);

    List<CommentTag> getAllByIdIn(List<Long> ids);


}
