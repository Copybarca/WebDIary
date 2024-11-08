package org.diary.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import org.diary.model.Note;
import org.diary.model.Topic;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NoteDaoImpl implements CrudDAONote{

    SessionFactory sessionFactory;
    Session session = null;
    Topic topic;
    @Autowired
    public NoteDaoImpl(SessionFactory sessionFactory, Topic topic) {
        this.sessionFactory = sessionFactory;
        this.topic = topic;
    }
    @Override
    public void update(int id, Object object) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public List<?> index() {
        List<Note> list = null;

        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            //List<Client> list2 = session.createQuery("from Client", Client.class).getResultList();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Note> cq = cb.createQuery(Note.class);
            Root<Note> root = cq.from(Note.class);
            CriteriaQuery<Note> all = cq.select(root);
            TypedQuery<Note> allQuery = session.createQuery(all);
            list = allQuery.getResultList();

            session.getTransaction().commit();
        }catch (HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return list;
    }
    public List<Note> indexByTopicsId(Integer topicId){// вытягивает из бд все объекы данного типа
        List<Note> list= null;

        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Note> criteriaQuery = criteriaBuilder.createQuery(Note.class);
            Root<Note> rootEntry = criteriaQuery.from(Note.class);

            ParameterExpression<Integer> param = criteriaBuilder.parameter(Integer.class);

            CriteriaQuery<Note> all = criteriaQuery.select(rootEntry).where(criteriaBuilder.equal(rootEntry.get("topic_id"),topicId));
            TypedQuery<Note> allQuery = session.createQuery(all);
            list = allQuery.getResultList();

            session.getTransaction().commit();
        }catch(HibernateException e){
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

    @Override
    public void create(Note note) {
        try{
            Topic topicToSave = new Topic(note.getDescription());
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
}
