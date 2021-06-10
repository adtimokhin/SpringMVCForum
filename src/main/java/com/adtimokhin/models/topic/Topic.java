package com.adtimokhin.models.topic;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author adtimokhin
 * 14.04.2021
 **/

@Entity
@Table(name = "table_topics")
@Getter
@Setter
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String topic;

    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "topic")
    private List<Comment> comments;

    @OneToMany(mappedBy = "topic")
    private List<Report> reports;

    @ManyToMany
    @JoinTable(
            name = "tagged_topics",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns =  @JoinColumn(name = "tag_id")
    )
    private Set<TopicTag> tags;

    @Column(name = "isClosed")
    private boolean isClosed;
}
