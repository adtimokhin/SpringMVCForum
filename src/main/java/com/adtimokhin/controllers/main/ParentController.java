package com.adtimokhin.controllers.main;

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
 * 22.04.2021
 **/
@Controller
@RequestMapping("parent")
public class ParentController {

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
    private ContextProvider contextProvider;

    @GetMapping("/topics")
    public String getAllTopics(Model model) {
        model.addAttribute("topics", topicService.getAllTopics());
        return "/parent/parentForumPage";
    }

    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable(name = "id") long id, Model model) {
        Topic topic = topicService.getTopic(id);
        if (topic == null) {
            // TODO: throw a 404 error
            return "redirect:/parent/topics";
        }
        List<Comment> comments = commentService.getAllCommentsByTopic(id);
        List<Comment> flaggedComments = commentService.getFlaggedComment(comments);
        if (flaggedComments != null) {
            model.addAttribute("flaggedComments", flaggedComments);
        }
        User u = contextProvider.getUser();
        List<Long> likedCommentIds = likeService.getAllLikedCommentIdsByUser(u, comments);
        model.addAttribute("likedComments", likedCommentIds);
        model.addAttribute("topic", topic);
        model.addAttribute("comments", comments);
        model.addAttribute("commentTags", tagsService.getAllCommentTags());

        //some special functionality is only available to a user that have initiated the topic.
        // We need to check if a user that gets the view is the same user that have created the topic.
        if(topicService.isUserCreatedTopic(topic, u)){
            model.addAttribute("theCreator" , true);
        }else {
            model.addAttribute("theCreator", false);
        }

        if(topic.isClosed()){
            model.addAttribute("closed" , true);
        }else {
            model.addAttribute("closed" , false);
        }


        return "/parent/parentTopicPage";
    }


    @GetMapping("/add/topic")
    public String studentAddTopic(Model model) {
        model.addAttribute("topic", new Topic());
        return "/parent/parentNewTopicPage";
    }

    @PostMapping("/add/topic")
    public String studentAddTopic(@ModelAttribute Topic topic, BindingResult result) {
        topicValidator.validate(topic, result);
        if (result.hasErrors()) {
            return "/parent/parentNewTopicPage";
        }
        topicService.addTopic(topic);
        return "redirect:/parent/topics";

    }

    @PostMapping("/update/topic/close")
    public String closeTopic(@RequestParam(name = "topicId") long topicId){
        User user = contextProvider.getUser();
        topicService.closeTopic(topicId, user);
        return "redirect:/parent/topics";
    }

    @PostMapping("/update/topic/open")
    public String openTopic(@RequestParam(name = "topicId") long topicId){
        User user = contextProvider.getUser();
        topicService.openTopic(topicId, user);
        return "redirect:/parent/topics";
    }

    //adding a new comment
    @PostMapping("/add/comment")
    public String studentAddComment(@RequestParam(name = "msg") String msg,
                                    @RequestParam(name = "topicId") long topicId,
                                    @RequestParam(name = "tags", required = false) List<Long> tags) {

        commentService.addComment(msg, topicId, tags);
        return "redirect:/parent/topic/" + topicId;
    }

    @PostMapping("/update/comment/flag")
    public String flagComment(@RequestParam(name = "commentId") long commentId){
        commentService.flagComment(commentId, contextProvider.getUser());
        return "redirect:/parent/topics";
    }

    @PostMapping("/add/like")
    public String parentAddLike(@RequestParam(name = "comment") long commentId) {
        Comment comment = commentService.getCommentById(commentId);// Todo: это обращение к бд - ненужное и зря нагружает систему. Нужно это как-то пофиксить
        likeService.addLike(contextProvider.getUser(), comment);
        return "redirect:/parent/topics";
    }

    // removing a like from a comment
    @PostMapping("/remove/like")
    public String parentRemoveLike(@RequestParam(name = "comment") long commentId) {
        Comment comment = commentService.getCommentById(commentId); // Todo: это обращение к бд - ненужное и зря нагружает систему. Нужно это как-то пофиксить
        likeService.deleteLike(contextProvider.getUser(), comment);
        return "redirect:/parent/topics";
    }

    // working with reports and bans
    @PostMapping("add/report")
    public String addReport(
            @RequestParam(name = "commentOrTopicId") long commentOrTopicId,
            @RequestParam(name = "isComment") Boolean isComment,
            @RequestParam(name = "reportedUserId") long reportedUserId,
            @RequestParam(name = "causeId") long causeId
    ) {
        reportService.addReport(commentOrTopicId, isComment, reportedUserId, contextProvider.getUser().getId(), causeId);
        return "redirect:/parent/topics";
    }
}
