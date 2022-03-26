package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retrieveAll();

    void store(T obj);

    boolean removeItemById(Integer objIdToRemove);
}
