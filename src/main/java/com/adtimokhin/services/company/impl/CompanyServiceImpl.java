package com.adtimokhin.services.company.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.company.CompanyRepository;
import com.adtimokhin.services.company.CompanyService;
import com.adtimokhin.services.company.TokenService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Component
public class CompanyServiceImpl implements CompanyService {
    //Repositories
    @Autowired
    private CompanyRepository repository;


    //Services
    @Autowired
    private TokenService tokenService;

    private static final Logger logger = Logger.getLogger("file");
    private static final Logger adminLogger = Logger.getLogger("admin");

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
    public void verifyCompany(User admin, long id) {

        if (admin == null) {
            logger.info("Null user tried to verify a company with id " + id);
            return;
        }

        if (!admin.getRoles().contains(Role.ROLE_ADMIN)) {
            logger.info("User with id " + admin.getId() + " have tried to verify a company with id " + id + " without having a role ADMIN.");
            return;
        }

        Company company = repository.findById(id);

        if (company == null) {
            logger.info("Company with id " + id + " was not found in the system, though still tried to e verified by a " +
                    "user with id " + admin.getId());
            return;
        }

        if (!company.isVerified()) {
            tokenService.generateTokens(company);
            company.setVerified(true);
            repository.save(company);
            adminLogger.info("Admin with id " + admin.getId() + " verified a company with id " + id + ". " + company.getTokens() + " tokens have been generated for that company ");
        } else {
            logger.info("Admin with id " + admin.getId() + " tried to verify already verified company with id " + id);
        }
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
    public Company getCompanyByPhone(String phone) {
        return repository.findByPhone(phone);
    }


}
