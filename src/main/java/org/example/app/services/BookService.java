package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepo;

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
    }

    public List<Book> getAllBooks() {
        return bookRepo.retrieveAll();
    }

    public void saveBook(Book book) {
        bookRepo.store(book);
    }

    public boolean removeBookById(Integer bookIdToRemove) {
        return bookRepo.removeItemById(bookIdToRemove);
    }

    public boolean removeBookByTitle(String bookTitleToRemove) {
        return bookRepo.removeItemByTitle(bookTitleToRemove);
    }

    public boolean removeBookByAuthor(String bookAuthorToRemove) {
        return bookRepo.removeItemByAuthor(bookAuthorToRemove);
    }

    public boolean removeBookBySize(Integer bookSizeToRemove) {
        return bookRepo.removeItemBySize(bookSizeToRemove);
    }

    public List<Book> getBookByTitle(String bookTitleForFilter) {
        return bookRepo.getItemByTitle(bookTitleForFilter);
    }

    public List<Book> getBookByAuthor(String bookAuthorForFilter) {
        return bookRepo.getItemByAuthor(bookAuthorForFilter);
    }

    public List<Book> getBookBySize(Integer bookSizeForFilter) {
        return bookRepo.getItemBySize(bookSizeForFilter);
    }
}
