package com.adtimokhin.models;

import javax.persistence.*;
import java.util.List;

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

}
