package com.adtimokhin.models.topic;

import javax.persistence.*;
import java.util.Set;

/**
 * @author adtimokhin
 * 08.05.2021
 **/
@Entity
@Table(name = "topic_tags")
public class TopicTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    /**
     * All tag names are stored in uppercase letters.
     **/
    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private Set<Topic> taggedTopics;


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

    public Set<Topic> getTaggedTopics() {
        return taggedTopics;
    }

    public void setTaggedTopics(Set<Topic> taggedTopics) {
        this.taggedTopics = taggedTopics;
    }
}
