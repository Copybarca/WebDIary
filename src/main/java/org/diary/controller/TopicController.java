package org.diary.controller;

import org.diary.dao.TopicDao;
import org.diary.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
@RequestMapping("/topics")
public class TopicController {
    private final TopicDao topicDAO;

    @Autowired
    public TopicController(TopicDao topicDAO) {
        this.topicDAO = topicDAO;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("TopicsList", topicDAO.index());
        return "topicView/index";
    }

    @GetMapping("/new")
    public String newTopic(Model model){
        model.addAttribute("topic",new Topic());
        return"topicView/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("topic")@Valid Topic topic, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return"topicView/new";
        }
        topicDAO.create(topic);
        return"redirect:/topics";
    }

}
