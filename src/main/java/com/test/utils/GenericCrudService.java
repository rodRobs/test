package com.test.utils;

import java.util.List;

public interface GenericCrudService<T> {

    List<T> findAll();

    T findById(int id);

    T save(T object);

    T update(int id, T object);

    void deleteById(int id);

}
