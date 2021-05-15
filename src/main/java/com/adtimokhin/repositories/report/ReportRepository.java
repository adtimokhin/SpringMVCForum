package com.adtimokhin.repositories.report;

import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author adtimokhin
 * 15.05.2021
 **/
@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    Report findById(long id);

    List<Report> findAllByReportedUser(User user);
}
