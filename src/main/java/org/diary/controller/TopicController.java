package org.diary.controller;

import org.diary.dao.NoteDaoImpl;
import org.diary.dao.TopicDao;
import org.diary.model.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("/topics")
public class TopicController {
    private final TopicDao topicDAO;
    private final NoteDaoImpl noteDao;

    @Autowired
    public TopicController(TopicDao topicDAO, NoteDaoImpl noteDao) {
        this.topicDAO = topicDAO;
        this.noteDao = noteDao;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("TopicsList", topicDAO.index());
        model.addAttribute("notesWithoutTopicsList", noteDao.indexNoteWithoutTopic());
        return "topicView/indexPage";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") Long id, Model model){
        model.addAttribute("topic", topicDAO.show(id));
        model.addAttribute("notesList",noteDao.indexByTopicsId(id));
        return "topicView/show";
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
