package com.adtimokhin.models.like;

import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author adtimokhin
 * 01.05.2021
 **/
@Entity
@Table(name = "table_likes")
@Getter
@Setter
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
}
