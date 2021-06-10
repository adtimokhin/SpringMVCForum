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
@Table(name = "user_first_names")
@Getter
@Setter
public class UserName {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "userName")
    private List<User> users;

}
