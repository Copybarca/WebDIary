package org.diary.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.diary.model.Topic;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TopicDao implements CrudDAOTopic{
    SessionFactory sessionFactory;
    Session session = null;
    Topic topic;
    @Autowired
    public TopicDao(SessionFactory sessionFactory, Topic topic) {
        this.sessionFactory = sessionFactory;
        this.topic = topic;
    }

    @Override
    public void create(Topic topic) {
        try{
            Topic topicToSave = new Topic(topic.getTitle());
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.persist(topicToSave);
            session.getTransaction().commit();
        }catch(HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public void update(int id, Object object) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<Topic> index() {
        List<Topic> list = null;

        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            //List<Client> list2 = session.createQuery("from Client", Client.class).getResultList();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Topic> cq = cb.createQuery(Topic.class);
            Root<Topic> root = cq.from(Topic.class);
            CriteriaQuery<Topic> all = cq.select(root);
            TypedQuery<Topic> allQuery = session.createQuery(all);
            list = allQuery.getResultList();

            session.getTransaction().commit();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return list;
    }

    @Override
    public Object show(int id) {
        return null;
    }
}
