package com.adtimokhin.services.company.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.company.CompanyRepository;
import com.adtimokhin.services.company.CompanyService;
import com.adtimokhin.services.company.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Component
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private CompanyRepository repository;



    @Autowired
    private TokenService tokenService;

    @Override
    public void addCompany(Company company) {
        repository.save(company);
    }

    @Override
    public void addCompany(String name, String url, String email, String phone, String location, int tokens) {
        Company company = new Company();
        company.setName(name);
        company.setWebsiteURL(url);
        company.setEmail(email);
        company.setPhone(phone);
        company.setLocation(location);
        company.setTokens(tokens);
        company.setVerified(false);

        repository.save(company);
    }

    @Override
    public void verify(User admin, long id) {

        if(admin == null){
            return;
        }

        if(!admin.getRoles().contains(Role.ROLE_ADMIN)){
            return;
        }

        Company company = repository.findById(id);

        if(company == null){
            return;
        }

        tokenService.generateTokens(company);
        company.setVerified(true);
        repository.save(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return repository.findAll();
    }

    @Override
    public List<Company> getAllPendingCompanies() {
        return repository.getAllByVerifiedIs(false);
    }

    @Override
    public List<Company> getAllVerifiedCompanies() {
        return repository.getAllByVerifiedIs(true);
    }

    @Override
    public Company getByPhone(String phone) {
        return repository.findByPhone(phone);
    }


}
