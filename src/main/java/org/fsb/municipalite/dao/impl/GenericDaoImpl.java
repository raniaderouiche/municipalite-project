package org.fsb.municipalite.dao.impl;

import org.fsb.municipalite.dao.IGenericDao;

import javax.persistence.*;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericDaoImpl<E> implements IGenericDao<E> {

    @PersistenceContext
    EntityManager em;

    protected Class<E> type;

    public Class<E> getType() {
        return type;
    }

    public GenericDaoImpl() {

        //create an entity manager
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("todo");
        em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<E>) pt.getActualTypeArguments()[0];
    }

    @Override
    public E save(E entity) {
        em.persist(entity);
        em.getTransaction().commit();
        return entity;
    }

    @Override
    public E update(E entity) {
        em.merge(entity);
        em.getTransaction().commit();
        return entity;
    }

    @Override
    public List<E> selectAll() {
        //em.getTransaction().begin();
        Query query = em.createQuery("select t from " + type.getSimpleName() + " t");
        List<E> list = query.getResultList();
        return list;
    }

    @Override
    public List<E> selectAll(String sortField, String sort) {
        Query query = em.createQuery("select t from " + type.getSimpleName() + " t order by " + sortField + " " + sort);
        return query.getResultList();
    }

    @Override
    public List<E> selectBy(String param, String value) {
        Query query = em.createQuery("select t from " + type.getSimpleName() + " t where "+param+" = "+value);
        List<E> list = query.getResultList();
        return list;
    }

    @Override
    public E getById(Long id) {
        //em.getTransaction().begin();
        E entity = em.find(type, id);
        return entity;
    }
    

    @Override
    public void remove(Long id) {
        //em.getTransaction().begin();
        E tab = em.getReference(type, id);
        em.remove(tab);
        em.getTransaction().commit();
    }

    @Override
    public E findOne(String paramName, Object paramValue) {
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
        Query query = em.createQuery("select t from " + type.getSimpleName() + " t where "+param+" LIKE "+value);
        List<E> list = query.getResultList();
        return list;
    }
    
    @Override
    public List<E> selectAllInColumn(String param) {
        Query query = em.createQuery("select " + param +" from " + type.getSimpleName() + "");
        List<E> list = query.getResultList();
        return list;
    }

}
