package org.diary.dao;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
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
    public void delete(Long id) {
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaDelete<Note> delete = criteriaBuilder.createCriteriaDelete(Note.class);
            Root<Note> rootEntry = delete.from(Note.class);
            delete.where(criteriaBuilder.equal(rootEntry.get("id"),id));
            session.createQuery(delete).executeUpdate();
            session.getTransaction().commit();
        }catch (HibernateException e){
            e.printStackTrace();//TODO: тут нужно добавить сквозную логику логирования через аспекты Spring для каждого исключения всех DAO методов
        }finally {
            session.close();
        }
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
            e.printStackTrace();//TODO: тут нужно добавить сквозную логику логирования через аспекты Spring для каждого исключения всех DAO методов
        }finally {
            session.close();
        }
        return list;
    }
    public List<Note> indexByTopicsId(Long topicId){// вытягивает из бд все объекы у которых единый топик
        List<Note> list= null;

        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Note> criteriaQuery = criteriaBuilder.createQuery(Note.class);
            Root<Note> rootEntry = criteriaQuery.from(Note.class);
            CriteriaQuery<Note> all = criteriaQuery.select(rootEntry).where(criteriaBuilder.equal(rootEntry.get("topic").get("id"),topicId));
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
    public Note show(Long id) {
        Note note = null;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            note = session.get(Note.class, id);
            session.getTransaction().commit();

        }catch(HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return note;
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

    @Override
    public void update(Long id, Note note) {
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Note noteToUpdate =session.get(Note.class, id);
            noteToUpdate.setDescription(topic.getTitle());
            session.getTransaction().commit();
        }catch(HibernateException e){
            e.printStackTrace();
        }finally {
            session.close();
        }
    }

    public List<Note> indexNoteWithoutTopic(){
        List<Note> list= null;
        try{
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Note> criteriaQuery = criteriaBuilder.createQuery(Note.class);
            Root<Note> rootEntry = criteriaQuery.from(Note.class);
            CriteriaQuery<Note> all = criteriaQuery.select(rootEntry).where(criteriaBuilder.isNull(rootEntry.get("topic").get("id")));
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
}
