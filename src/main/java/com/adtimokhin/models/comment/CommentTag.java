package com.adtimokhin.models.comment;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author adtimokhin
 * 03.05.2021
 **/

@Entity
@Table(name = "comment_tags")
@Getter
@Setter
public class CommentTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long id;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private Set<Comment> taggedComments;
}
