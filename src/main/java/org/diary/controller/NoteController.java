package org.diary.controller;

import org.diary.dao.NoteDaoImpl;
import org.diary.model.Note;
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
@RequestMapping("/topics/notes")
public class NoteController {
    private final NoteDaoImpl noteDaoImpl;

    @Autowired
    public NoteController(NoteDaoImpl noteDaoImpl) {
        this.noteDaoImpl = noteDaoImpl;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("NotesList", noteDaoImpl.index());
        return "notesView/index";
    }

    @GetMapping("/new")
    public String newTopic(Model model){
        model.addAttribute("note",new Note());
        return"notesView/new";
    }
    @PostMapping()
    public String create(@ModelAttribute("note")@Valid Note note, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return"notesView/new";
        }
        noteDaoImpl.create(note);
        return"redirect:/topics/notes";
    }
}
