package com.adtimokhin.models.like;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.user.User;

import javax.persistence.*;

/**
 * @author adtimokhin
 * 01.05.2021
 **/
@Entity
@Table(name = "table_likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }
}
