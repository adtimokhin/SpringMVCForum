package com.adtimokhin.services;

import com.adtimokhin.models.CommentTag;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author adtimokhin
 * 03.05.2021
 **/
@Service
public interface CommentTagsService {

    CommentTag getCommentTagById(long id);

    Set<CommentTag> getCommentTagsByIds(List<Long> ids);

    List<CommentTag> getAllCommentTags();
}
