package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book>, ApplicationContextAware {

    private final Logger logger = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();
    private ApplicationContext context;

    @Override
    public List<Book> retreiveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(context.getBean(IdProvider.class).provideId(book));
        logger.info("store new book: " + book);
        repo.add(book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        for (Book book : retreiveAll()) {
            if (book.getId().equals(bookIdToRemove)) {
                logger.info("remove book completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    public boolean removeItemByTitle(String bookTitleToRemove) {
        int count = 0;
        Pattern pattern = Pattern.compile(bookTitleToRemove);
        for (Book book : retreiveAll()) {
            Matcher matcher = pattern.matcher(book.getTitle());
            if (matcher.find()) {
                count++;
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
        return count > 0;
    }

    public boolean removeItemByAuthor(String bookAuthorToRemove) {
        int count = 0;
        Pattern pattern = Pattern.compile(bookAuthorToRemove);
        for (Book book : retreiveAll()) {
            Matcher matcher = pattern.matcher(book.getAuthor());
            if (matcher.find()) {
                count++;
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
        return count > 0;
    }

    public boolean removeItemBySize(Integer bookSizeToRemove) {
        int count = 0;
        Pattern pattern = Pattern.compile(String.valueOf(bookSizeToRemove));
        for (Book book : retreiveAll()) {
            Matcher matcher = pattern.matcher(String.valueOf(book.getSize()));
            if (matcher.find()) {
                count++;
                logger.info("remove book completed: " + book);
                repo.remove(book);
            }
        }
        return count > 0;
    }


    public List<Book> getItemByTitle(String bookTitleForFilter) {
        List<Book> books = new ArrayList<>();
        Pattern pattern = Pattern.compile(bookTitleForFilter);
        for (Book book : repo) {
            Matcher matcher = pattern.matcher(book.getTitle());
            if (matcher.find()) {
                books.add(book);
            }
        }
        return books;
    }

    public List<Book> getItemByAuthor(String bookAuthorForFilter) {
        List<Book> books = new ArrayList<>();
        Pattern pattern = Pattern.compile(bookAuthorForFilter);
        for (Book book : repo) {
            Matcher matcher = pattern.matcher(book.getAuthor());
            if (matcher.find()) {
                books.add(book);
            }
        }
        return books;
    }

    public List<Book> getItemBySize(Integer bookSizeForFilter) {
        List<Book> books = new ArrayList<>();
        Pattern pattern = Pattern.compile(String.valueOf(bookSizeForFilter));
        for (Book book : repo) {
            Matcher matcher = pattern.matcher(String.valueOf(book.getSize()));
            if (matcher.find()) {
                books.add(book);
            }
        }
        return books;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
