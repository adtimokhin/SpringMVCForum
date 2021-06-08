package com.adtimokhin.repositories.company;

import com.adtimokhin.models.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    Company findById(long id);

    List<Company> getAllByVerifiedIsFalse();


}
