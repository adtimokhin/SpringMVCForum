package com.adtimokhin.services.company.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.company.CompanyRepository;
import com.adtimokhin.services.company.CompanyService;
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

        company.setVerified(true);
        repository.save(company);
        generateTokens(company);
    }

    @Override
    public List<Company> getAllCompanies() {
        return repository.findAll();
    }

    @Override
    public List<Company> getAllPendingCompanies() {
        return repository.getAllByVerifiedIsFalse();
    }

    @Override
    public void generateTokens(Company company) {
        if (!company.isVerified()){
            return;
        }


    }
}
