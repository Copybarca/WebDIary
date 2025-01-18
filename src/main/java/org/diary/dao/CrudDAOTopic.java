package org.diary.dao;

import org.diary.model.Topic;

public interface CrudDAOTopic extends CrudDAO{
    void create(Topic topic);
    void update(Long id, Topic topic);
}
