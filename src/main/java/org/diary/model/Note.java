package org.diary.model;

import jakarta.persistence.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@Scope("prototype")
@Entity
@Table(name = "note")
public class Note {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)//Дочерние объекты Note будут выгружаться в память только по первому запросу к нему, до этого ресурсы памяти заполняться не будут.
    @JoinColumn (name = "topic_id")// Внешний ключ, указывающий на столбец id в таблице topic
    private Topic topic;


    public Note(){}
    public void setTopic(Topic topic){
        this.topic=topic;
    }
    public Topic getTopic(){
        //Топика может не быть - будет null.
        return this.topic;

    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
