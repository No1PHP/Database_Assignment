package com.dao.inters;

import java.util.Collection;

/**
 * @author xiaos
 * @param <T> entity type
 */
public interface iDAO_handler<T> {
    int insert(T obj);
    int insert(Collection<T> obj);

    int delete(T obj);
    int delete(Collection<T> obj);

    int update(T obj);
    int update(Collection<T> obj);

    Collection<T> query(String statement);
}
