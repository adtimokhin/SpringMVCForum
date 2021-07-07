package com.adtimokhin.models.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 07.07.2021
 **/

@Entity
@Table(name = "table_rating")
@Getter
@Setter
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "min_rating")
    private long minRating;

    @Column(name = "max_rating")
    private long maxRating;

    @Column(name = "rating_name")
    private String name;

    @OneToMany(mappedBy = "ratingStatus")
    private List<User> users;
}
