package com.adtimokhin.models.user;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 08.05.2021
 **/

@Entity
@Table(name = "user_last_names")
public class UserSurname {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "userSurname")
    private List<User> users;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
