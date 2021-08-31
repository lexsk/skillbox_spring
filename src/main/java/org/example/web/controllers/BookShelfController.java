package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/books")
public class BookShelfController {

    private Logger logger = Logger.getLogger(BookShelfController.class);
    private BookService bookService;

    @Autowired
    public BookShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/shelf")
    public String books(Model model) {
        logger.info("got book shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(Book book) {
        if (book.isEmpty()) {
            logger.info("You can't insert an empty record");
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove/id")
    public String removeBookById(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
        if (bookService.removeBookById(bookIdToRemove)) {
            logger.info("book id=" + bookIdToRemove + " removed");
        } else {
            logger.info("book id=" + bookIdToRemove + " was NOT removed!");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove/title")
    public String removeBookByTitle(@RequestParam(value = "bookTitleToRemove") String bookTitleToRemove) {
        if (bookService.removeBookByTitle(bookTitleToRemove)) {
            logger.info("books title=" + bookTitleToRemove + " removed");
        } else {
            logger.info("books title=" + bookTitleToRemove + " was NOT removed!");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove/author")
    public String removeBookByAuthor(@RequestParam(value = "bookAuthorToRemove") String bookAuthorToRemove) {
        if (bookService.removeBookByAuthor(bookAuthorToRemove)) {
            logger.info("books Author=" + bookAuthorToRemove + " removed");
        } else {
            logger.info("books Author=" + bookAuthorToRemove + " was NOT removed!");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/remove/size")
    public String removeBookBySize(@RequestParam(value = "bookSizeToRemove") Integer bookSizeToRemove) {
        if (bookService.removeBookBySize(bookSizeToRemove)) {
            logger.info("books size=" + bookSizeToRemove + " removed");
        } else {
            logger.info("books size=" + bookSizeToRemove + " was NOT removed!");
        }
        return "redirect:/books/shelf";
    }

    @PostMapping("/filter/title")
    public String filterBookByTitle(Model model, @RequestParam(value = "bookTitleToFilter") String bookTitleToFilter) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getBookByTitle(bookTitleToFilter));
        return "book_shelf";
    }

    @PostMapping("/filter/author")
    public String filterBookByAuthor(Model model, @RequestParam(value = "bookAuthorToFilter") String bookAuthorToFilter) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getBookByAuthor(bookAuthorToFilter));
        return "book_shelf";
    }

    @PostMapping("/filter/size")
    public String filterBookBySize(Model model, @RequestParam(value = "bookSizeToFilter") Integer bookSizeToFilter) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getBookBySize(bookSizeToFilter));
        return "book_shelf";
    }

    @PostMapping("/filter/reset")
    public String resetFilter() {
        return "redirect:/books/shelf";
    }
}
