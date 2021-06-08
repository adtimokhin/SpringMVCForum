package com.adtimokhin.services.company.impl;

import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.company.Token;
import com.adtimokhin.repositories.company.TokenRepository;
import com.adtimokhin.services.company.TokenService;
import com.adtimokhin.utils.TokenGenerator;
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

    @Autowired
    private TokenRepository repository;

    @Autowired
    private TokenGenerator tokenGenerator;

    @Override
    public void safe(String token, Company company) {
        Token t = new Token();
        t.setCompany(company);
        t.setTokenValue(token);

        repository.save(t);
    }

    @Override
    public Token getSampleToken(Company company) {
        return repository.getFirstByCompany(company);
    }

    @Override
    public List<Token> getAllTokens(Company company) {
        return repository.getAllByCompany(company);
    }

    @Override
    public void generateTokens(Company company) {
        ArrayList<String> tok = (ArrayList<String>) tokenGenerator.generateTokens(company, company.getTokens());
        for (String t :
                tok) {
            safe(t, company);
        }
    }
}
