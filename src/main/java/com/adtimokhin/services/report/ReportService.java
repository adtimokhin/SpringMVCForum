package com.adtimokhin.services.report;

import com.adtimokhin.models.report.Cause;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author adtimokhin
 * 15.05.2021
 **/

@Service
public interface ReportService {

    List<Report> getAllReports();

    Report getReportById(long id);

    List<Report> getAllReportsByReportedUser(User user);

    List<Report> getAllReportsByReportedUser(long id);

    void addReport(long commentOrTopicId, int textType , long reportedUserId, long reportingUserId, Cause cause);

    void banUser(Report report, String reason, User admin);

    void unBanUser(User user, User admin);


    void dismissReport(long reportId, String reason, User user);
}
