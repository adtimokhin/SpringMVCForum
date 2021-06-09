package com.adtimokhin.models.user;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.company.Token;
import com.adtimokhin.models.like.Like;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.topic.Topic;
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

    @OneToOne(mappedBy = "user")
    private Token token;

    // constructors
    public User() {
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public UserName getUserName() {
        return userName;
    }

    public void setUserName(UserName userName) {
        this.userName = userName;
    }

    public UserSurname getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(UserSurname userSurname) {
        this.userSurname = userSurname;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public List<Like> getLikes() {
        return likes;
    }

    public void setLikes(List<Like> likes) {
        this.likes = likes;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public List<Report> getReportsForUser() {
        return reportsForUser;
    }

    public void setReportsForUser(List<Report> reportsForUser) {
        this.reportsForUser = reportsForUser;
    }

    public List<Report> getReportsByUser() {
        return reportsByUser;
    }

    public void setReportsByUser(List<Report> reportsByUser) {
        this.reportsByUser = reportsByUser;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((User) obj).getId();
    }

    //methods
    public String getFullName() {
        String name = getUserName().getName();
        String surname = getUserSurname().getSurname();
        return surname + " " + name;
    }
}
