package org.diary.model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("prototype")
@Entity
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    @Column
    private String title;
    //orphanRemoval = true удаляет записку из базы и записка больше не выводится. если false, записка в базе останется, но её внешний ключ будет null
    @OneToMany(mappedBy="topic", fetch=FetchType.LAZY, cascade = CascadeType.ALL) // MappedBy указывает гланую сущность, cascade указывает операции каскадного изменения для дочерних объектов
    private  List<Note> noteList;

    public Topic() {
    }
    public Topic(String text){
        title =text;
    }
    public List<Note> getNoteList(){
        return this.noteList;
    }
    public void addNote(Note note){
        noteList.add(note);
        note.setTopic(this);
    }
    public void removeNote(Note note){
        noteList.remove(note);
        note.setTopic(null);
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
