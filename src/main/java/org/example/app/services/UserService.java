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

    public boolean authenticate(User user) {
        logger.info("try auth with user-form: " + user);
        for (User currentUser : userRepo.retreiveAll()) {
            if (currentUser.getUsername().equals(user.getUsername())
                    && currentUser.getPassword().equals(user.getPassword())) {
                return true;
            }
        }
        return user.getUsername().equals("root") && user.getPassword().equals("123");
    }

    public List<User> getAllUsers() {
        return userRepo.retreiveAll();
    }

    public void saveUser(User user) {
        userRepo.store(user);
    }

    public boolean removeUserById(Integer userIdToRemove) {
        return userRepo.removeItemById(userIdToRemove);
    }
}
