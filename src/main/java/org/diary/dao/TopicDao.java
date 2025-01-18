package org.diary.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
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
    public void update(Long id, Topic topic){
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Topic topicToUpdate =session.get(Topic.class, id);
            topicToUpdate.setTitle(topic.getTitle());
            session.getTransaction().commit();
        }catch(HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    @Override
    public void delete(Long id) {
        Topic topic = new Topic();
        topic.setId(id);
        try{
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<Topic> criteriaDelete = cb.createCriteriaDelete(Topic.class);
            Root<Topic> root = criteriaDelete.from(Topic.class);
            criteriaDelete.where(cb.equal(root.get(topic.getId().toString()), id));
            //The equivalent JPQL: DELETE FROM Employee e WHERE e.id = 'Mike'
            session.getTransaction().begin();
            session.getTransaction().commit();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
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
    public Object show(Long id) {
        Topic topic = null;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            topic = session.get(Topic.class, id);
            session.getTransaction().commit();

        }catch(HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return topic;
    }
}
