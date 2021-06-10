package com.adtimokhin.models.topic;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

/**
 * @author adtimokhin
 * 08.05.2021
 **/
@Entity
@Table(name = "topic_tags")
@Getter
@Setter
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
}
