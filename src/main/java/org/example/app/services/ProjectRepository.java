package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {
    List<T> retreiveAll();

    void store(T obj);

    boolean removeItemById(String objIdToRemove);
}
