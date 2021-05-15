package com.adtimokhin.repositories.report;

import com.adtimokhin.models.report.Cause;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author adtimokhin
 * 15.05.2021
 **/

public interface CauseRepository extends JpaRepository <Cause, Long>{
    Cause findById(long id);
}
