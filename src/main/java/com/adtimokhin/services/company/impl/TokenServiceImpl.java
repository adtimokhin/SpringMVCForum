package com.adtimokhin.services.company.impl;

import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.company.Token;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.company.CompanyRepository;
import com.adtimokhin.repositories.company.TokenRepository;
import com.adtimokhin.services.company.TokenService;
import com.adtimokhin.utils.TokenGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Component
public class TokenServiceImpl implements TokenService {

    //Repositories
    @Autowired
    private TokenRepository repository;

    @Autowired
    private CompanyRepository companyRepository;


    @Autowired
    private TokenGenerator tokenGenerator;

    private static final Logger logger = Logger.getLogger("file");


    @Override
    public void addToken(String token, Company company) {
        if (company == null) {
            logger.info("Tried to add a token to a null company");
            return;
        }
        Token t = new Token();
        t.setCompany(company);
        t.setTokenValue(token);

        repository.save(t);
    }

    @Override
    public Token getSampleToken(Company company) {
        if (company == null) {
            logger.info("Tried to get a token for a null company");
            return null;
        }
        return repository.getFirstByCompany(company);
    }

    @Override
    public List<Token> getAllTokens(Company company) {
        if (company == null) {
            logger.info("Tried to get tokens for a null company");
            return null;
        }
        return repository.getAllByCompany(company);
    }

    @Override
    public void generateTokens(Company company) {
        if (company == null) {
            logger.info("Tried to generate tokens for a null company");
            return;
        }
        ArrayList<String> tok = (ArrayList<String>) tokenGenerator.generateTokens(company, company.getTokens());
        for (String t :
                tok) {
            addToken(t, company);
        }
    }

    @Override
    public Token getToken(String tokenValue) {
        return repository.getByTokenValue(tokenValue);
    }

    @Override
    public void setUser(Token token, User user) {
        if (user == null) {
            logger.info("Tried to set a token to a null user.");
        }
        token.setUser(user);
        companyRepository.save(token.getCompany());
        repository.save(token);
    }
}
