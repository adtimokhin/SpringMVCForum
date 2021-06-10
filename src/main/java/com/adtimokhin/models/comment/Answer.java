package com.adtimokhin.models.comment;

import com.adtimokhin.models.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author adtimokhin
 * 10.06.2021
 **/

@Entity
@Table(name = "table_answers", schema = "public")
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "answer_text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

}
