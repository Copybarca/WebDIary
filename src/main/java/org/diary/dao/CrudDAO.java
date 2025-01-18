package org.diary.dao;

import org.diary.model.Topic;

import java.util.List;

public interface CrudDAO {
     void delete(Long id);
     List<?> index();
     Object show(Long id);
}
