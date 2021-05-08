package com.adtimokhin.repositories;

import com.adtimokhin.models.CommentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 03.05.2021
 **/
@Repository
public interface CommentTagsRepository extends JpaRepository<CommentTag, Long> {

    CommentTag getByTagName(String tagName);

    CommentTag getById(long id);

    List<CommentTag> getAllByIdIn(List<Long> ids);


}
