package org.diary.dao;

import org.diary.model.Note;

public interface CrudDAONote extends CrudDAO{
    void create(Note note);
}
