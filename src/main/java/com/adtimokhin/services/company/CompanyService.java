package com.adtimokhin.services.company;

import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.user.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author adtimokhin
 * 08.06.2021
 **/
@Service
public interface CompanyService {

    @Transactional
    void addCompany(Company company);

    @Transactional
    void addCompany(String name, String url, String email, String phone, String location, int tokens);

    @Transactional
    void verify(User admin,long id);

    List<Company> getAllCompanies();

    List<Company> getAllPendingCompanies();

    List<Company> getAllVerifiedCompanies();

}
