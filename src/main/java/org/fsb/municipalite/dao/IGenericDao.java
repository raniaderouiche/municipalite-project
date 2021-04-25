package org.fsb.municipalite.dao;

import java.util.List;

public interface IGenericDao<E> {

    public E save(E entity);

    public E update(E entity);

    public List<E> selectAll();

    public List<E> selectAll(String sortField, String sort);

    public List<E> selectBy(String param, String value);

    public E getById(Long id);

    public void remove(Long id);

    public E findOne(String paramName, Object paramValue);

    public E findOne(String[] paramNames, Object[] paramValues);

    public int findCountBy(String paramName, String paramValue);

    public List<E> selectLike(String param, String value);
    
    public List<E> selectAllInColumn(String param);


}
