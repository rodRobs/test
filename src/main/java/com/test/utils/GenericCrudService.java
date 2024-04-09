package com.test.utils;

import java.util.List;

public interface GenericCrudService<T, ID> {

    List<T> findAll();

    T findById(ID id);

    T save(T object);

    T update(ID id, T object);

    void deleteById(ID id);

}
