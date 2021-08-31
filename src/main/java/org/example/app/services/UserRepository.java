package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRepository implements ProjectRepository<User> {

    private final Logger logger = Logger.getLogger(UserRepository.class);
    private final List<User> repo = new ArrayList<>();

    @Override
    public List<User> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(User user) {
        user.setId(user.hashCode());
        logger.info("store new book: " + user);
        repo.add(user);
    }

    @Override
    public boolean removeItemById(Integer userIdToRemove) {
        for (User user : retreiveAll()) {
            if (user.getId().equals(userIdToRemove)) {
                logger.info("remove book completed: " + user);
                return repo.remove(user);
            }
        }
        return false;
    }
}
