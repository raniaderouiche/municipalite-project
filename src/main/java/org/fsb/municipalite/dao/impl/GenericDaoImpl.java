package org.fsb.municipalite.dao.impl;

import org.fsb.municipalite.App;
import org.fsb.municipalite.dao.IGenericDao;
import org.hibernate.SessionFactory;

import javax.persistence.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericDaoImpl<E> implements IGenericDao<E> {

    @PersistenceContext
    EntityManager em;

    SessionFactory entityManagerFactory = App.getCurrentSessionFromJPA();

    protected Class<E> type;

    public Class<E> getType() {
        return type;
    }

    public GenericDaoImpl() {
        //create an entity manager
        //EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("todo");
        em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<E>) pt.getActualTypeArguments()[0];
    }

    @Override
    public E save(E entity) {
        //entityManagerFactory = App.getCurrentSessionFromJPA();
        em.persist(entity);
        em.getTransaction().commit();
        entityManagerFactory.close();
        return entity;
    }

    @Override
    public E update(E entity) {
        //entityManagerFactory = App.getCurrentSessionFromJPA();
        em.merge(entity);
        em.getTransaction().commit();
        entityManagerFactory.close();
        return entity;
    }

    @Override
    public List<E> selectAll() {
        //entityManagerFactory = App.getCurrentSessionFromJPA();

        Query query = em.createQuery("select t from " + type.getSimpleName() + " t");
        List<E> list = query.getResultList();
        entityManagerFactory.close();
        return list;
    }

    @Override
    public List<E> selectAll(String sortField, String sort) {
        //entityManagerFactory = App.getCurrentSessionFromJPA();
        Query query = em.createQuery("select t from " + type.getSimpleName() + " t order by " + sortField + " " + sort);
        return query.getResultList();
    }

    @Override
    public List<E> selectBy(String param, String value) {
        //entityManagerFactory = App.getCurrentSessionFromJPA();
        Query query = em.createQuery("select t from " + type.getSimpleName() + " t where "+param+" = "+value);
        List<E> list = query.getResultList();
        return list;
    }

    @Override
    public E getById(Long id) {
        //entityManagerFactory = App.getCurrentSessionFromJPA();
        E entity = em.find(type, id);
        return entity;
    }
    

    @Override
    public void remove(Long id) {
        E tab = em.getReference(type, id);
        em.remove(tab);
        em.getTransaction().commit();
        entityManagerFactory.close();
    }

    @Override
    public E findOne(String paramName, Object paramValue) {
        //entityManagerFactory = App.getCurrentSessionFromJPA();
        Query query = em.createQuery("select t from " + type.getSimpleName() + " t where " + paramName + " = :x");
        query.setParameter("x", paramValue);
        return query.getResultList().size() > 0 ? (E) query.getResultList().get(0) : null;
    }

    @Override
    public E findOne(String[] paramNames, Object[] paramValues) {
        if (paramNames.length != paramValues.length) {
            return null;
        }
        String queryString = "select e from " + type.getSimpleName() + " e where ";
        int len = paramNames.length;
        for (int i = 0; i < len; i++) {
            queryString += " e." + paramNames[i] + "= :x" + i;
            if ((i + 1) < len) {
                queryString += " and ";
            }
        }
        Query query = em.createQuery(queryString);
        for (int i = 0; i < paramValues.length; i++) {
            query.setParameter("x" + i, paramValues[i]);
        }
        return query.getResultList().size() > 0 ? (E) query.getResultList().get(0) : null;
    }

    @Override
    public int findCountBy(String paramName, String paramValue) {
        Query query = em.createQuery("select t from " + type.getSimpleName() + " t where " + paramName + " = :x");
        query.setParameter(paramName, paramValue);
        return query.getResultList().size() > 0 ? ((Long) query.getSingleResult()).intValue() : 0;
    }

    @Override
    public List<E> selectLike(String param, String value) {
        //entityManagerFactory = App.getCurrentSessionFromJPA();
        Query query = em.createQuery("select t from " + type.getSimpleName() + " t where "+param+" LIKE "+value);
        List<E> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<E> selectAllInColumn(String param) {
        //entityManagerFactory = App.getCurrentSessionFromJPA();
        Query query = em.createQuery("select " + param +" from " + type.getSimpleName() + "");
        List<E> list = query.getResultList();
        return list;
    }

}
