package com.adtimokhin.models.comment;

import com.adtimokhin.models.like.Like;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
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

    @OneToMany(mappedBy = "comment")
    private List<Report> reports;

    @OneToMany(fetch = FetchType.EAGER, targetEntity = Answer.class, mappedBy = "comment")
    private List<Answer> answers;

    @ManyToMany
    @JoinTable(
            name = "tagged_comments",
            joinColumns = @JoinColumn(name = "comment_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<CommentTag> tags;

    @Column(name = "flagged")
    private boolean flagged;

    public String getTagNames() {
        return Arrays.toString(this.getTags().stream().map(CommentTag::getTagName).collect(Collectors.toList()).toArray());
    }
}
