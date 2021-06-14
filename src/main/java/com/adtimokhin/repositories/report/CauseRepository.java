package com.adtimokhin.repositories.report;

import com.adtimokhin.models.report.Cause;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author adtimokhin
 * 15.05.2021
 **/

public interface CauseRepository extends JpaRepository <Cause, Long>{
    Cause findById(long id);
    Cause findByTitle(String title);

    List<Cause> findAllByBasicIsTrue();
}
