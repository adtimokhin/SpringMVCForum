package com.adtimokhin.models.topic;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.user.User;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * @author adtimokhin
 * 14.04.2021
 **/

@Entity
@Table(name = "table_topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String topic;

    private String description;

//    @Column(name = "user_id")
//    private long userId;

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


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<TopicTag> getTags() {
        return tags;
    }

    public void setTags(Set<TopicTag> tags) {
        this.tags = tags;
    }
}
