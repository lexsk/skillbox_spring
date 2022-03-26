package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.UserService;
import org.example.web.app.exceptions.BookShelfLoginException;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final Logger logger = Logger.getLogger(UserController.class);
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public String list(Model model) {
        logger.info("GET /login returns login_page.html");
        model.addAttribute("user", new User());
        model.addAttribute("userList", userService.getAllUsers());
        return "users";
    }

    @PostMapping("/save")
    public String saveBook(User user) {
        if (user.isEmpty()) {
            logger.info("You can't insert an empty record");
        } else {
            userService.saveUser(user);
            logger.info("current repository size: " + userService.getAllUsers().size());
        }
        return "redirect:/user/list";
    }

    @PostMapping("/remove")
    public String removeBook(@RequestParam(value = "userIdToRemove") Integer userIdToRemove, Model model) {
        if (userService.removeUserById(userIdToRemove)) {
            logger.info("book id=" + userIdToRemove + " removed");
        } else {
            logger.info("book id=" + userIdToRemove + " was NOT removed!");
        }
        return "redirect:/user/list";
    }

    @ExceptionHandler(BookShelfLoginException.class)
    public String handleError(Model model, BookShelfLoginException e) {
        model.addAttribute("errorMessage", e.getMessage());
        return "errors/login_err";
    }
}
