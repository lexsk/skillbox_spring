package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.AuthorService;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public MainPageController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping("/")
    public String rootPage(Model model) {
        return mainPage(model);
    }

    @GetMapping("/bookshop/main")
    public String mainPage(Model model) {
        model.addAttribute("bookData", bookService.getBooksData());
        model.addAttribute("searchPlaceholder", "new search placeholder");
        return "index";
    }

    @GetMapping("/bookshop/genres")
    public String genresPage(Model model) {
        return "genres/index";
    }

    @GetMapping("/bookshop/authors")
    public String authorsPage(Model model) {
        model.addAttribute("authorData", authorService.getAuthorsData());
        return "authors/index";
    }
}
