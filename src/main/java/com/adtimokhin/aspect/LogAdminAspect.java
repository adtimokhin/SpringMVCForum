package com.adtimokhin.aspect;

import com.adtimokhin.models.comment.Answer;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.report.Report;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author adtimokhin
 * 15.05.2021
 **/
@Component
@Aspect
public class LogAdminAspect {

    final private static Logger logger = Logger.getLogger("admin");


    @After("execution(* com.adtimokhin.services.user.UserService.banUser(..))")
    public void afterUserBannedInvocation(JoinPoint jp) {
        System.out.println("User " + ((User) jp.getArgs()[0]).getId() + " was banned at " + System.currentTimeMillis());
        logger.info("User " + ((User) jp.getArgs()[0]).getId() + " was banned");
    }

    @Before("execution(* com.adtimokhin.services.report.ReportService.banUser(..))")
    public void beforeReportedUserBannedInvocation(JoinPoint jp) {
        Object[] args = jp.getArgs();

        Report report = (Report) args[0];
        String reason = (String) args[1];
        User admin = (User) args[2];


        Comment comment = report.getComment();
        if (comment == null) {
            Topic topic = report.getTopic();
            if(topic == null){
                Answer answer = report.getAnswer();
                System.out.println("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                        " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN TOPIC "
                        + answer.getId() + " WAS SOLVED BY ADMIN " + admin.getId() +
                        ". REPORTED USER WAS BANNED. REASON:" + reason);
                logger.info("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                        " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN TOPIC "
                        + answer.getId() + " WAS SOLVED BY ADMIN " + admin.getId() +
                        ". REPORTED USER WAS BANNED. REASON:" + reason);
            }else {
                System.out.println("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                        " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN TOPIC "
                        + topic.getId() + " WAS SOLVED BY ADMIN " + admin.getId() +
                        ". REPORTED USER WAS BANNED. REASON:" + reason);
                logger.info("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                        " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN TOPIC "
                        + topic.getId() + " WAS SOLVED BY ADMIN " + admin.getId() +
                        ". REPORTED USER WAS BANNED. REASON:" + reason);
            }
        } else {
            System.out.println("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                    " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN COMMENT"
                    + comment.getId() + " WAS SOLVED BY ADMIN " + admin.getId() +
                    ". REPORTED USER WAS BANNED. REASON:" + reason);
            logger.info("REPORT ID:" + report.getId() + " REPORT ON " + report.getReportedUser().getId() +
                    " BY " + report.getReportingUser().getId() + " ON ISSUE OF " + report.getCause() + " IN COMMENT"
                    + comment.getId() + " WAS SOLVED BY ADMIN " + admin.getId() +
                    ". REPORTED USER WAS BANNED. REASON:" + reason);
        }

    }

    @After("execution(* com.adtimokhin.services.report.ReportService.unBanUser(..))")
    public void afterUserUnBannedInvocation(JoinPoint jp) {
        User u = (User) jp.getArgs()[0];
        User admin = (User) jp.getArgs()[1];

        System.out.println("User " + u.getId() + " was unbanned at " + System.currentTimeMillis() + " by admin " + admin.getId());
        logger.info("User " + u.getId() + " was unbanned at " + System.currentTimeMillis() + " by admin " + admin.getId());
    }
}
