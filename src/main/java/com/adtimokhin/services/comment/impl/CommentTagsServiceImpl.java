package com.adtimokhin.services.comment.impl;

import com.adtimokhin.models.comment.CommentTag;
import com.adtimokhin.repositories.comment.CommentTagRepository;
import com.adtimokhin.services.comment.CommentTagsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author adtimokhin
 * 03.05.2021
 **/

@Component
public class CommentTagsServiceImpl implements CommentTagsService {

    //Repositories
    @Autowired
    private CommentTagRepository repository;


    @Override
    public CommentTag getCommentTagById(long id) {
        return repository.getById(id);
    }

    @Override
    public Set<CommentTag> getCommentTagsByIds(List<Long> ids) {
        return new HashSet<>(repository.getAllByIdIn(ids));
    }

    @Override
    public List<CommentTag> getAllCommentTags() {
        return repository.findAll();
    }
}
