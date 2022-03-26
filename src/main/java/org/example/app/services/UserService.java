package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final ProjectRepository<User> userRepo;
    private Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    public UserService(ProjectRepository<User> userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> getAllUsers() {
        return userRepo.retrieveAll();
    }

    public void saveUser(User user) {
        userRepo.store(user);
    }

    public boolean removeUserById(Integer userIdToRemove) {
        return userRepo.removeItemById(userIdToRemove);
    }
}
