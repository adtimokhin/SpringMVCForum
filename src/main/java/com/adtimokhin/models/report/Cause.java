package com.adtimokhin.models.report;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 15.05.2021
 **/

@Entity
@Table(name = "table_cause")
@Getter
@Setter
public class Cause {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cause_title")
    private String title;

    @OneToMany(mappedBy = "cause")
    private List<Report> reports;

}
