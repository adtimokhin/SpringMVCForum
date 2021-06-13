package com.adtimokhin.controllers.main;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.comment.AnswerService;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.comment.impl.CommentTagsServiceImpl;
import com.adtimokhin.services.like.LikeService;
import com.adtimokhin.services.report.ReportService;
import com.adtimokhin.services.topic.TopicService;
import com.adtimokhin.services.user.UserService;
import com.adtimokhin.utils.validator.TopicValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author adtimokhin
 * 09.06.2021
 **/

@Controller
public class TopicController {


    @Autowired
    private ContextProvider contextProvider;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicValidator topicValidator;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private CommentTagsServiceImpl tagsService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private UserService userService;

    private static final Logger logger = Logger.getLogger("file");


    @GetMapping("/topics")
    public String getAllTopics(Model model) {
        // Check that a user that had logged in had ever logged in before.

        User user = contextProvider.getUser();
        if (userService.isFirstTime(user)) {
            userService.setUserEnteredTheForum(user);
            return "redirect:/intro";
        }

        if (isStudent()) {
            model.addAttribute("topics", topicService.getAllTopicsForStudents());
        } else {
            model.addAttribute("topics", topicService.getAllTopics());
        }
        return "main/pages/forumPage";
    }


    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable(name = "id") String stringId, Model model) throws NoHandlerFoundException {
        int id = 0;
        try {
            id = Integer.parseInt(stringId);
        } catch (NumberFormatException e) {
            throw new NoHandlerFoundException("GET", "/topic/" + stringId, new HttpHeaders());
        }

        Topic topic = topicService.getTopic(id);
        if (topic == null) {
            throw new NoHandlerFoundException("GET", "/topic/" + id, new HttpHeaders());
        }
        if (!topicService.isUserAllowedOntoTopic(topic, contextProvider.getUser())) {
            return "error/accessDeniedPage";
        }
        List<Comment> comments = commentService.getAllCommentsByTopic(id);
        List<Comment> flaggedComments = commentService.getFlaggedComment(comments);
        if (flaggedComments != null) {
            model.addAttribute("flaggedComments", flaggedComments);
        }
        User user = contextProvider.getUser();
        List<Long> likedCommentIds = likeService.getAllLikedCommentIdsByUser(user, comments);
        model.addAttribute("likedComments", likedCommentIds);
        model.addAttribute("topic", topic);
        model.addAttribute("comments", comments);
        model.addAttribute("commentTags", tagsService.getAllCommentTags());

        //some special functionality is only available to a user that have initiated the topic.
        // We need to check if a user that gets the view is the same user that have created the topic
        if (topicService.isUserCreatedTopic(topic, user)) {
            model.addAttribute("theCreator", true);
        } else {
            model.addAttribute("theCreator", false);
        }

        if (topic.isClosed()) {
            model.addAttribute("closed", true);
        } else {
            model.addAttribute("closed", false);
        }
        return "/main/pages/topicPage";
    }


    //working with topics

    //adding a new topic
    @GetMapping("/add/topic")
    public String studentAddTopic() {
        return "/main/pages/newTopicPage";
    }


    @PostMapping("/add/topic")
    public String getAddTopic(@RequestParam(name = "topic") String topic,
                              @RequestParam(name = "description") String description, Model model) {
        ArrayList<String> errors = topicValidator.validate(topic, description);
        if (errors != null) {
            model.addAttribute("errors", errors);
            return "/main/pages/newTopicPage";
        }
        Topic topic1 = new Topic();
        topic1.setTopic(topic);
        topic1.setDescription(description);
        topicService.addTopic(topic1);
        return "redirect:/topics";
    }

    @PostMapping("/update/topic/close")
    public String closeTopic(@RequestParam(name = "topicId") long topicId) {
        User user = contextProvider.getUser();
        topicService.closeTopic(topicId, user);
        return "redirect:/topics";
    }


    @PostMapping("/update/topic/open")
    public String openTopic(@RequestParam(name = "topicId") long topicId) {
        User user = contextProvider.getUser();
        topicService.openTopic(topicId, user);
        return "redirect:/topics";
    }

    //working with comments

    //adding a new comment
    @PostMapping("/add/comment")
    public String studentAddComment(@RequestParam(name = "msg") String msg,
                                    @RequestParam(name = "topicId") long topicId,
                                    @RequestParam(name = "tags", required = false) List<Long> tags) {

        if (!topicService.isUserAllowedOntoTopic(topicService.getTopic(topicId), contextProvider.getUser())) {
            return "error/accessDeniedPage";
        }

        commentService.addComment(msg, topicId, tags);
        return "redirect:/topic/" + topicId;
    }

    @PostMapping("/update/comment/flag")
    public String flagComment(@RequestParam(name = "commentId") long commentId) {
        commentService.flagComment(commentId, contextProvider.getUser());
        return "redirect:/topics";
    }

    // add a like to a comment
    @PostMapping("/add/like")
    public String studentAddLike(@RequestParam(name = "comment") long commentId) throws NoHandlerFoundException {
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            throw new NoHandlerFoundException("GET", "/add/like/" + commentId, new HttpHeaders());
        }
        if (!topicService.isUserAllowedOntoTopic(comment.getTopic(), contextProvider.getUser())) {
            return "error/accessDeniedPage";
        }
        likeService.addLike(contextProvider.getUser(), comment);
        return "redirect:/topics";
    }

    // removing a like from a comment
    @PostMapping("/remove/like")
    public String studentRemoveLike(@RequestParam(name = "comment") long commentId) throws NoHandlerFoundException {
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            throw new NoHandlerFoundException("GET", "/remove/like/" + commentId, new HttpHeaders());
        }
        if (!topicService.isUserAllowedOntoTopic(comment.getTopic(), contextProvider.getUser())) {
            return "error/accessDeniedPage";
        }
        likeService.deleteLike(contextProvider.getUser(), comment);
        return "redirect:/topics";
    }

    // working with reports and bans
    @PostMapping("add/report")
    public String addReport(
            @RequestParam(name = "commentOrTopicId") long commentOrTopicId,
            @RequestParam(name = "isComment") Boolean isComment,
            @RequestParam(name = "reportedUserId") long reportedUserId,
            @RequestParam(name = "causeId") long causeId
    ) throws NoHandlerFoundException {
        // only is user is allowed on the topic should he/she create a report.
        if (isComment) {
            Comment comment = commentService.getCommentById(commentOrTopicId);
            if (comment ==null){
                logger.error("Tried to add a report on to a null comment with id "+commentOrTopicId);
                throw new NoHandlerFoundException("GET", "/add/report/" + commentOrTopicId, new HttpHeaders());
            }
            if (!topicService.isUserAllowedOntoTopic(comment.getTopic(), contextProvider.getUser())) {
                return "error/accessDeniedPage";
            }
        } else {
            Topic topic = topicService.getTopic(commentOrTopicId);
            if (topic ==null){
                logger.error("Tried to add a report on to a null topic with id "+commentOrTopicId);
                throw new NoHandlerFoundException("GET", "/add/report/" + commentOrTopicId, new HttpHeaders());
            }
            if (!topicService.isUserAllowedOntoTopic(topic, contextProvider.getUser())) {
                return "error/accessDeniedPage";
            }
        }
        reportService.addReport(commentOrTopicId, isComment, reportedUserId, contextProvider.getUser().getId(), causeId);
        return "redirect:/topics";
    }


    //working with answers

    @PostMapping("add/answer")
    public String addAnswer(@RequestParam(name = "text") String text,
                            @RequestParam(name = "comment_id") long commentId) {
        answerService.addAnswer(text, contextProvider.getUser(), commentId);
        return "redirect:/topics";
    }


    private Role getRole() {
        return (Role) contextProvider.getUser().getRoles().toArray()[0];
    }

    private boolean isStudent() {
        return getRole().equals(Role.ROLE_STUDENT);
    }
}
