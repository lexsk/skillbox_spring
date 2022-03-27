package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping("/save")
    public String saveBook(@Valid Book book, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                // добавляем в модель ошибки специфичные для определенных полей, чтобы на фронте отображать только нужные, а не все возможные ошибки
                model.addAttribute(error.getField() + "_err", true);
            }
            model.addAttribute("book", book);
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.saveBook(book);
            logger.info("current repository size: " + bookService.getAllBooks().size());
            return "redirect:/books/shelf";
        }

    }

    @PostMapping("/remove/id")
    public String removeBookById(@Valid BookIdToRemove bookIdToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("book id=" + bookIdToRemove + " was NOT removed!");
            model.addAttribute("book", new Book());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookById(bookIdToRemove.getId());
            logger.info("book id=" + bookIdToRemove + " removed");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove/title")
    public String removeBookByTitle(@Valid BookTitleToRemove bookTitleToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("books title=" + bookTitleToRemove.getTitle() + " was NOT removed!");
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookByTitle(bookTitleToRemove.getTitle());
            logger.info("books title=" + bookTitleToRemove.getTitle() + " removed");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove/author")
    public String removeBookByAuthor(@Valid BookAuthorToRemove bookAuthorToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("books author=" + bookAuthorToRemove.getAuthor() + " was NOT removed!");
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookByAuthor(bookAuthorToRemove.getAuthor());
            logger.info("books author=" + bookAuthorToRemove.getAuthor() + " removed");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/remove/size")
    public String removeBookBySize(@Valid BookSizeToRemove bookSizeToRemove, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("books size=" + bookSizeToRemove.getSize() + " was NOT removed!");
            model.addAttribute("book", new Book());
            model.addAttribute("bookIdToRemove", new BookIdToRemove());
            model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
            model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
            model.addAttribute("bookList", bookService.getAllBooks());
            return "book_shelf";
        } else {
            bookService.removeBookBySize(bookSizeToRemove.getSize());
            logger.info("books size=" + bookSizeToRemove.getSize() + " removed");
            return "redirect:/books/shelf";
        }
    }

    @PostMapping("/filter")
    public String filterBookByTitle(Model model,
                                    @RequestParam(value = "titleToFilter", required = false) String titleToFilter,
                                    @RequestParam(value = "authorToFilter", required = false) String authorToFilter,
                                    @RequestParam(value = "sizeToFilter", required = false) Integer sizeToFilter
    ) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
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
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws Exception {
        String name = file.getOriginalFilename();
        if (!"".equals(name)) {
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
        } else {
            model.addAttribute("file_upload_err", true);
            logger.info("Не выбран файл для загрузки");
        }
        model.addAttribute("book", new Book());
        model.addAttribute("bookIdToRemove", new BookIdToRemove());
        model.addAttribute("bookTitleToRemove", new BookTitleToRemove());
        model.addAttribute("bookAuthorToRemove", new BookAuthorToRemove());
        model.addAttribute("bookSizeToRemove", new BookSizeToRemove());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

}
