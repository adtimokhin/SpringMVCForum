package com.adtimokhin.services.report.impl;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.comment.Answer;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.report.Cause;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;
import com.adtimokhin.repositories.report.ReportRepository;
import com.adtimokhin.services.comment.AnswerService;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.report.CauseService;
import com.adtimokhin.services.report.ReportService;
import com.adtimokhin.services.topic.TopicService;
import com.adtimokhin.services.user.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author adtimokhin
 * 15.05.2021
 **/

@Component
public class ReportServiceImpl implements ReportService {
    //Repositories
    @Autowired
    private ReportRepository repository;

    //Services
    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private CauseService causeService;


    private static final Logger logger = Logger.getLogger("file");
    private static final Logger adminLogger = Logger.getLogger("admin");

    @Override
    public List<Report> getAllReports() {
        return repository.findAll();
    }

    @Override
    public Report getReportById(long id) {
        return repository.findById(id);
    }

    @Override
    public List<Report> getAllReportsByReportedUser(User user) {
        if (user == null) {
            logger.info("Tried to get reports by a null reported user");
            return null;
        }
        return repository.findAllByReportedUser(user);
    }

    @Override
    public List<Report> getAllReportsByReportedUser(long id) {
        return repository.findAllByReportedUser(userService.getUser(id));
    }

    @Override
    public void addReport(long commentOrTopicId, int textType, long reportedUserId, long reportingUserId,
                          Cause cause) {
        Report report = new Report();

        if (textType == 1) {
            report.setComment(commentService.getCommentById(commentOrTopicId));
        } else if (textType == 2) {
            report.setTopic(topicService.getTopic(commentOrTopicId));
        } else if (textType == 3) {
            report.setAnswer(answerService.getAnswer(commentOrTopicId));
        } else {
            logger.info("Tried to add a report to a neither comment, topic, nor answer. reportedUserId:" + reportedUserId + " reportingUserId:" + reportingUserId + " cause" + cause.getTitle());
            return;
        }

        report.setReportedUser(userService.getUser(reportedUserId));
        report.setReportingUser(userService.getUser(reportingUserId));
        report.setCause(cause);

        repository.save(report);
    }

    @Override
    public void banUser(Report report, String reason, User admin) {
        if (admin == null) {
            logger.info("Null user tried to ban another user");
            adminLogger.info("The report was malformed");
            return;
        }
        if (report == null) {
            logger.info("User with id " + admin.getId() + " tried to ban another user");
            adminLogger.info("The report was malformed");
            return;
        }

        if (admin.getRoles().contains(Role.ROLE_ADMIN)) {
            userService.banUser(report.getReportedUser());
            repository.delete(report);
        } else {
            logger.info("A user with id " + admin.getId() + " tried to block a user for a report with id " + report.getId() + " thought that user is not an ADMIN");
            adminLogger.info("The report was malformed");
        }
    }

    @Override
    public void unBanUser(User user, User admin) {
        if (user == null || admin == null) {
            logger.info("Either admin or a unbanned user was a null.");
            adminLogger.info("The report was malformed");
            return;
        }

        if (admin.getRoles().contains(Role.ROLE_ADMIN)) {
            userService.unBanUser(user);
        } else {
            logger.info("A user with id " + admin.getId() + " tried to unban user with id " + user.getId() + ", though that user was not an ADMIN");
        }
    }

    @Override
    public void dismissReport(long reportId, String reason, User user) {
        if (user == null) {
            logger.info("Null user tried to dismiss a report with id " + reportId);
            adminLogger.info("The report was malformed");
            return;
        }
        Report report = getReportById(reportId);
        if (report == null) {
            logger.info("User with id " + user.getId() + " tried to ban another user");
            adminLogger.info("The report was malformed");
            return;
        }
        if (user.getRoles().contains(Role.ROLE_ADMIN)) {
            Comment comment = report.getComment();
            if (comment == null) {
                Topic topic = report.getTopic();
                if (topic == null) { //todo: move it to some other place. There is just too much of the logging messages.
                    Answer answer = report.getAnswer();
                    System.out.println("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                            " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN ANSWER "
                            + answer.getId() + " WAS SOLVED BY ADMIN " + user.getId() +
                            ". REPORTED USER WAS BANNED. REASON:" + reason);
                    logger.info("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                            " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN ANSWER "
                            + answer.getId() + " WAS SOLVED BY ADMIN " + user.getId() +
                            ". REPORTED USER WAS BANNED. REASON:" + reason);
                } else {
                    System.out.println("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                            " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN TOPIC "
                            + topic.getId() + " WAS SOLVED BY ADMIN " + user.getId() +
                            ". REPORTED USER WAS BANNED. REASON:" + reason);
                    logger.info("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                            " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN TOPIC "
                            + topic.getId() + " WAS SOLVED BY ADMIN " + user.getId() +
                            ". REPORTED USER WAS BANNED. REASON:" + reason);
                }
            } else {
                System.out.println("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                        " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN COMMENT"
                        + comment.getId() + " WAS SOLVED BY ADMIN " + user.getId() +
                        ". REPORTED USER WAS BANNED. REASON:" + reason);
                logger.info("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                        " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN COMMENT"
                        + comment.getId() + " WAS SOLVED BY ADMIN " + user.getId() +
                        ". REPORTED USER WAS BANNED. REASON:" + reason);
            }
            repository.delete(report);
        } else {
            logger.info("A user with id " + user.getId() + " tried to dismiss a report with id " + report.getId() + " thought that user is not an ADMIN");
            adminLogger.info("The report was malformed");
        }

    }
}
