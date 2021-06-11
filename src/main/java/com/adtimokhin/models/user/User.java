package com.adtimokhin.models.user;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.comment.Answer;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.company.Token;
import com.adtimokhin.models.like.Like;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.topic.Topic;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * @author adtimokhin
 * 10.04.2021
 **/

@Entity
@Table(name = "table_users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Email(message = "please, enter valid email")
    private String email;

    @Size(min = 5, message = "your password should be at least 5 figures long")
    private String password;

    @ManyToOne
    @JoinColumn(name = "user_name")
    private UserName userName;

    @ManyToOne
    @JoinColumn(name = "user_surname")
    private UserSurname userSurname;

    @Column(name = "banned")
    private boolean banned = false;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "table_roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "name")
    @ElementCollection(fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    private List<Comment> comments;

    @OneToMany(mappedBy = "user")
    private List<Topic> topics;

    @OneToMany(mappedBy = "user")
    private List<Like> likes;

    @OneToMany(mappedBy = "reportedUser")
    private List<Report> reportsForUser;

    @OneToMany(mappedBy = "reportingUser")
    private List<Report> reportsByUser;

    @OneToMany(mappedBy = "user")
    private List<Answer> answers;

    @OneToOne(mappedBy = "user")
    private Token token;

    @Column(name = "first_time")
    private boolean firstTime;

    @Column(name = "email_verification_token")
    private String emailVerificationToken;


    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((User) obj).getId();
    }

    public String getFullName() {
        String name = getUserName().getName();
        String surname = getUserSurname().getSurname();
        return surname + " " + name;
    }
}
