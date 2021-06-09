package com.adtimokhin.controllers.main;

import com.adtimokhin.enums.Role;
import com.adtimokhin.models.comment.Comment;
import com.adtimokhin.models.topic.Topic;
import com.adtimokhin.models.user.User;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.comment.CommentService;
import com.adtimokhin.services.comment.impl.CommentTagsServiceImpl;
import com.adtimokhin.services.like.LikeService;
import com.adtimokhin.services.report.ReportService;
import com.adtimokhin.services.topic.TopicService;
import com.adtimokhin.utils.validator.TopicValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/topics")
    public String getAllTopics(Model model) {
        if (isStudent()){
            model.addAttribute("topics", topicService.getAllTopicsForStudents());
        }else {
            model.addAttribute("topics", topicService.getAllTopics());
        }
        return "main/pages/forumPage";
    }


    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable(name = "id") long id, Model model) {
        Topic topic = topicService.getTopic(id);
        if (topic == null) {
            // TODO: throw a 404 error
            return "redirect:/topics";
        }
        if (!topicService.isUserAllowedOntoTopic(topic, contextProvider.getUser())) {
            return "redirect:/topics";//Todo:redirect to some error page (access denied page, e.g.)
        }
        List<Comment> comments = commentService.getAllCommentsByTopic(id);
        List<Comment> flaggedComments = commentService.getFlaggedComment(comments);
        if (flaggedComments != null){
            model.addAttribute("flaggedComments" , flaggedComments);
        }
        User user = contextProvider.getUser();
        List<Long> likedCommentIds = likeService.getAllLikedCommentIdsByUser(user, comments);
        model.addAttribute("likedComments", likedCommentIds);
        model.addAttribute("topic", topic);
        model.addAttribute("comments", comments);
        model.addAttribute("commentTags", tagsService.getAllCommentTags());

        //some special functionality is only available to a user that have initiated the topic.
        // We need to check if a user that gets the view is the same user that have created the topic
        if(topicService.isUserCreatedTopic(topic, user)){
            model.addAttribute("theCreator" , true);
        }else {
            model.addAttribute("theCreator", false);
        }

        if(topic.isClosed()){
            model.addAttribute("closed" , true);
        }else {
            model.addAttribute("closed" , false);
        }
        return "/main/pages/topicPage";
    }


    //working with topics

    //adding a new topic
    @GetMapping("/add/topic")
    public String studentAddTopic(Model model) {
        model.addAttribute("topic", new Topic());
        return "/main/pages/newTopicPage";
    }

    @PostMapping("/add/topic")
    public String studentAddTopic(@ModelAttribute Topic topic, BindingResult result) {
        topicValidator.validate(topic, result);
        if (result.hasErrors()) {
            return "/main/pages/newTopicPage"; // Todo: have a look at how errors are displayed.
        }
        topicService.addTopic(topic);
        return "redirect:/topics";

    }

    @PostMapping("/update/topic/close")
    public String closeTopic(@RequestParam(name = "topicId") long topicId){
        User user = contextProvider.getUser();
        topicService.closeTopic(topicId, user);
        return "redirect:/topics";
    }


    @PostMapping("/update/topic/open")
    public String openTopic(@RequestParam(name = "topicId") long topicId){
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

        if (!topicService.isUserAllowedOntoTopic(topicService.getTopic(topicId) , contextProvider.getUser())) {
            return "redirect:/topics"; //Todo:redirect to some error page (access denied page, e.g.)
        }

        commentService.addComment(msg, topicId, tags);
        return "redirect:/topic/" + topicId;
    }

    @PostMapping("/update/comment/flag")
    public String flagComment(@RequestParam(name = "commentId") long commentId){
        commentService.flagComment(commentId, contextProvider.getUser());
        return "redirect:/topics";
    }
    // add a like to a comment
    @PostMapping("/add/like")
    public String studentAddLike(@RequestParam(name = "comment") long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null){
            return "redirect:/topics"; //Todo:redirect to some error page (access denied page, e.g.)
        }
        if (!topicService.isUserAllowedOntoTopic(comment.getTopic() , contextProvider.getUser())) {
            return "redirect:/topics"; //Todo:redirect to some error page (access denied page, e.g.)
        }
        likeService.addLike(contextProvider.getUser(), comment);
        return "redirect:/topics";
    }

    // removing a like from a comment
    @PostMapping("/remove/like")
    public String studentRemoveLike(@RequestParam(name = "comment") long commentId) {
        Comment comment = commentService.getCommentById(commentId);
        if (comment == null){
            return "redirect:/topics"; //Todo:redirect to some error page (access denied page, e.g.)
        }
        if (!topicService.isUserAllowedOntoTopic(comment.getTopic() , contextProvider.getUser())) {
            return "redirect:/topics"; //Todo:redirect to some error page (access denied page, e.g.)
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
    ){
        // only is user is allowed on the topic should he/she create a report.
        if (isComment){
            Comment comment = commentService.getCommentById(commentOrTopicId);
            if (!topicService.isUserAllowedOntoTopic(comment.getTopic() , contextProvider.getUser())){
                return "redirect:/topics"; //Todo:redirect to some error page (access denied page, e.g.)
            }
        }else {
            Topic topic = topicService.getTopic(commentOrTopicId);
            if (!topicService.isUserAllowedOntoTopic(topic, contextProvider.getUser())){
                return "redirect:/topics"; //Todo:redirect to some error page (access denied page, e.g.)
            }
        }
        reportService.addReport(commentOrTopicId , isComment, reportedUserId, contextProvider.getUser().getId(), causeId);
        return "redirect:/topics";
    }
    private Role getRole() {
        return (Role) contextProvider.getUser().getRoles().toArray()[0];
    }

    //TODO: add to config files that now all urls associated with topics can be entered either by STUDENT PARENT and MEMBER.

    private boolean isStudent() {
        return getRole().equals(Role.ROLE_STUDENT);
    }
}
