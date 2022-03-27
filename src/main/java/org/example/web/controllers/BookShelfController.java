package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.example.web.dto.BookIdToRemove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

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
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            if (book.isEmpty()) {
                logger.info("You can't insert an empty record");
            } else {
                bookService.saveBook(book);
                logger.info("current repository size: " + bookService.getAllBooks().size());
            }
            return "redirect:/books/shelf";
        }

    }

    @PostMapping("/remove/id")
    public String removeBookById(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("book id=" + bookIdToRemove + " was NOT removed!");
            model.addAttribute("book", new Book());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            logger.info("book id=" + bookIdToRemove + " removed");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove/title")
    public String removeBookByTitle(@RequestParam(value = "bookTitleToRemove") String bookTitleToRemove, Model model) {
        if (bookService.removeBookByTitle(bookTitleToRemove)) {
            logger.info("books title=" + bookTitleToRemove + " removed");
        } else {
            logger.info("books title=" + bookTitleToRemove + " was NOT removed!");
        }
        return books(model);
    }

    @PostMapping("/remove/author")
    public String removeBookByAuthor(@RequestParam(value = "bookAuthorToRemove") String bookAuthorToRemove, Model model) {
        if (bookService.removeBookByAuthor(bookAuthorToRemove)) {
            logger.info("books Author=" + bookAuthorToRemove + " removed");
        } else {
            logger.info("books Author=" + bookAuthorToRemove + " was NOT removed!");
        }
        return books(model);
    }

    @PostMapping("/remove/size")
    public String removeBookBySize(@RequestParam(value = "bookSizeToRemove") Integer bookSizeToRemove, Model model) {
        if (bookService.removeBookBySize(bookSizeToRemove)) {
            logger.info("books size=" + bookSizeToRemove + " removed");
        } else {
            logger.info("books size=" + bookSizeToRemove + " was NOT removed!");
        }
        return books(model);
    }

    @PostMapping("/filter")
    public String filterBookByTitle(Model model,
                                    @RequestParam(value = "titleToFilter", required = false) String titleToFilter,
                                    @RequestParam(value = "authorToFilter", required = false) String authorToFilter,
                                    @RequestParam(value = "sizeToFilter", required = false) Integer sizeToFilter
    ) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        if (titleToFilter != null && titleToFilter.length() > 0) {
            model.addAttribute("bookList", bookService.getBookByTitle(titleToFilter));

        } else if (authorToFilter != null && authorToFilter.length() > 0) {
            model.addAttribute("bookList", bookService.getBookByAuthor(authorToFilter));

        } else if (sizeToFilter != null) {
            model.addAttribute("bookList", bookService.getBookBySize(sizeToFilter));
        }

        return "book_shelf";
    }

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();
        byte[] bytes = file.getBytes();

        //create dir
        String rootPath = System.getProperty("catalina.home");
        File dir = new File(rootPath + File.separator + "external_uploads");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        //create file
        File serverFile = new File(dir.getAbsolutePath() + File.separator + name);
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
        stream.write(bytes);
        stream.close();

        logger.info("new file saved at: " + serverFile.getAbsolutePath());

        return "redirect:/books/shelf";
    }

}
