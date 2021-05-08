package com.adtimokhin.models.comment;

import com.adtimokhin.models.like.Like;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author adtimokhin
 * 24.04.2021
 **/

@Entity
@Table(name = "table_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    @Column(name = "total_likes")
    private long totalLikes;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToMany(mappedBy = "comment")
    private List<Like> likes;

    @ManyToMany
    @JoinTable(
            name = "tagged_comments",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<CommentTag> tags;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTotalLikes() {
        return totalLikes;
    }

    public void setTotalLikes(long totalLikes) {
        this.totalLikes = totalLikes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public Set<CommentTag> getTags() {
        return tags;
    }

    public void setTags(Set<CommentTag> tags) {
        this.tags = tags;
    }

    public String getTagNames(){
         return Arrays.toString(this.getTags().stream().map(CommentTag::getTagName).collect(Collectors.toList()).toArray());
    }
}