package com.adtimokhin.services.comment;

import com.adtimokhin.models.comment.CommentTag;
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
