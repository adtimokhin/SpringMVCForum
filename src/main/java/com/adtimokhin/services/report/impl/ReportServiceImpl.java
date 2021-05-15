package com.adtimokhin.services.report.impl;

import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.report.CauseRepository;
import com.adtimokhin.repositories.report.ReportRepository;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.report.ReportService;
import com.adtimokhin.services.topic.TopicService;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 15.05.2021
 **/

@Component
public class ReportServiceImpl implements ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private CauseRepository causeRepository;


    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TopicService topicService;


    @Override
    public List<Report> getAll() {
        return reportRepository.findAll();
    }

    @Override
    public List<Report> getAllByReportedUser(User user) {
        return reportRepository.findAllByReportedUser(user);
    }

    @Override
    public List<Report> getAllByReportedUser(long id) {
        return reportRepository.findAllByReportedUser(userService.getUser(id));
    }

    @Override
    public void addReport(long commentOrTopicId, boolean isComment, long reportedUserId, long reportingUserId,
                          long causeId) {
        Report report = new Report();

        if (isComment) {
            report.setComment(commentService.getCommentById(commentOrTopicId));
        } else {
            report.setTopic(topicService.getTopic(commentOrTopicId));
        }

        report.setReportedUser(userService.getUser(reportedUserId));
        report.setReportingUser(userService.getUser(reportingUserId));
        report.setCause(causeRepository.findById(causeId));

        reportRepository.save(report);
    }
}
