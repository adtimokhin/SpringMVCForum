package com.adtimokhin.models.company;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * @author adtimokhin
 * 08.06.2021
 **/
@Entity
@Table(name = "table_company")
@Getter
@Setter
public class Company {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "company_name")
    private String name;

    @Column(name = "url")
    private String websiteURL;

    private String email;

    private String phone;

    private String location;

    private boolean verified;


    @Column(name = "tokens_quantity")
    private int tokens;

    @OneToMany(mappedBy = "company")
    private List<Token> tokenList;
}
