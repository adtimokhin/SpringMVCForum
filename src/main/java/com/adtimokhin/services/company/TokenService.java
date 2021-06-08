package com.adtimokhin.services.company;

import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.company.Token;
import com.adtimokhin.models.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Service
public interface TokenService {

    void safe(String token, Company company);

    Token getSampleToken(Company company);

    List<Token> getAllTokens(Company company);

    void generateTokens(Company company);

    Token getToken(String tokenValue);

    @Transactional
    void setUser(Token token, User user);
}
