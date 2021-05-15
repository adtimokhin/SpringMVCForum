package com.adtimokhin.services.report;

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

    List<Report> getAll();

    List<Report> getAllByReportedUser(User user);

    List<Report> getAllByReportedUser(long id);

    void addReport(long commentOrTopicId, boolean isComment , long reportedUserId, long reportingUserId, long causeId);



}
