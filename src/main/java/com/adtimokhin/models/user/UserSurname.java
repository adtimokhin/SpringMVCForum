package com.adtimokhin.models.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 08.05.2021
 **/

@Entity
@Table(name = "user_last_names")
@Getter
@Setter
public class UserSurname {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "surname")
    private String surname;

    @OneToMany(mappedBy = "userSurname")
    private List<User> users;

}
