package com.adtimokhin.models.report;

import com.adtimokhin.models.comment.Answer;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author adtimokhin
 * 15.05.2021
 **/

@Entity
@Table(name = "table_reports")
@Getter
@Setter
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "reported_user_id")
    private User reportedUser;

    @ManyToOne
    @JoinColumn(name = "reporting_user_id")
    private User reportingUser;

    @ManyToOne
    @JoinColumn(name = "cause_id")
    private Cause cause;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;
}
