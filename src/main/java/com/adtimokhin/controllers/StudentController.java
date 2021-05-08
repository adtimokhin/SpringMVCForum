package com.adtimokhin.controllers;

import com.adtimokhin.models.Comment;
import com.adtimokhin.models.Topic;
import com.adtimokhin.security.ContextProvider;
import com.adtimokhin.services.CommentService;
import com.adtimokhin.services.LikeService;
import com.adtimokhin.services.TopicService;
import com.adtimokhin.services.impl.CommentTagsServiceImpl;
import com.adtimokhin.utils.TopicValidator;
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
@RequestMapping("student")
public class StudentController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicValidator topicValidator;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private ContextProvider contextProvider;

    @Autowired
    private CommentTagsServiceImpl tagsService;


    @GetMapping("/topics")
    public String getAllTopics(Model model) {
        model.addAttribute("topics", topicService.getAllTopicsForStudents());
        return "/student/studentForumPage";
    }

    @GetMapping("/topic/{id}")
    public String getTopic(@PathVariable(name = "id") long id, Model model) {
        Topic topic = topicService.getTopic(id);
        if (topic == null) {
            // TODO: throw a 404 error
            return "redirect:/student/topics";
        }
        if (!topicService.isUserAllowedOntoTopic(topic)) {
            return "redirect:/student/topics";//Todo:redirect to some error page (access denied page, e.g.)
        }
        List<Comment> comments = commentService.getAllCommentsByTopic(id);
        List<Long> likedCommentIds = likeService.getAllLikedCommentIdsByUser(contextProvider.getUser(), comments);
        model.addAttribute("likedComments", likedCommentIds);
        model.addAttribute("topic", topic);
        model.addAttribute("comments", comments);
        model.addAttribute("commentTags", tagsService.getAllCommentTags());


//        if (comments == null){
//            model.addAttribute("comments", "No comments yet...");
//        }else {
//            model.addAttribute("comments", comments);
//        }
        return "/student/studentTopicPage";
    }


    //working with topics

    //adding a new topic
    @GetMapping("/add/topic")
    public String studentAddTopic(Model model) {
        model.addAttribute("topic", new Topic());
        return "/student/studentNewTopicPage";
    }

    @PostMapping("/add/topic")
    public String studentAddTopic(@ModelAttribute Topic topic, BindingResult result) {
        topicValidator.validate(topic, result);
        if (result.hasErrors()) {
            return "/student/studentNewTopicPage";
        }
        topicService.addTopic(topic);
        return "redirect:/student/topics";

    }


    //working with comments

    //adding a new comment
    @PostMapping("/add/comment")
    public String studentAddComment(@RequestParam(name = "msg") String msg,
                                    @RequestParam(name = "topicId") long topicId,
                                    @RequestParam(name = "tags", required = false) List<Long> tags) {

        if (!topicService.isUserAllowedOntoTopic(topicService.getTopic(topicId))) {
            return "redirect:/student/topics"; //Todo:redirect to some error page (access denied page, e.g.)
        }

        commentService.addComment(msg, topicId, tags);
        return "redirect:/student/topic/" + topicId;
    }

    // add a like to a comment
    @PostMapping("/add/like")
    public String studentAddLike(@RequestParam(name = "comment") long commentId) {
        Comment comment = commentService.getCommentById(commentId); // Todo: это обращение к бд - ненужное и зря нагружает систему. Нужно это как-то пофиксить
        likeService.addLike(contextProvider.getUser(), comment);
        return "redirect:/student/topics";
    }

    // removing a like from a comment
    @PostMapping("/remove/like")
    public String studentRemoveLike(@RequestParam(name = "comment") long commentId) {
        Comment comment = commentService.getCommentById(commentId); // Todo: это обращение к бд - ненужное и зря нагружает систему. Нужно это как-то пофиксить
        likeService.deleteLike(contextProvider.getUser(), comment);
        return "redirect:/student/topics";
    }

}
