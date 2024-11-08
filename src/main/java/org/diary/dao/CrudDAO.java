package org.diary.dao;

import org.diary.model.Topic;

import java.util.List;

public interface CrudDAO {

     void update(int id, Object object);
     void delete(int id);
     List<?> index();
     Object show(int id);
}
