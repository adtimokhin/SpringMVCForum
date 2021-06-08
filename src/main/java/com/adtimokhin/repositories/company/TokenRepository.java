package com.adtimokhin.repositories.company;

import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.company.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

    List<Token> getAllByCompany(Company company);

    Token getFirstByCompany(Company company);

    Token getByTokenValue(String tokenValue);
}
