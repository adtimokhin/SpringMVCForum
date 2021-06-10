package com.adtimokhin.models.company;

import com.adtimokhin.models.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Entity
@Table(name = "table_tokens")
@Getter
@Setter
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "token_value")
    private String tokenValue;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * First 3 digits of the token represent a unique organization number
     **/
    public String getOrganizationNumber() {
        return tokenValue.substring(0, 3);
    }

    /**
     * Last 3 digits of the token represent a unique user number for the specific organization
     **/
    public String getUserNumber() {
        return tokenValue.substring(3);
    }


}
