package com.adtimokhin.models;

import javax.persistence.*;
import java.util.Set;

/**
 * @author adtimokhin
 * 03.05.2021
 **/

@Entity
@Table(name = "comment_tags")
public class CommentTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private long id;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private Set<Comment> taggedComments;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<Comment> getTaggedComments() {
        return taggedComments;
    }

    public void setTaggedComments(Set<Comment> taggedComments) {
        this.taggedComments = taggedComments;
    }
}
