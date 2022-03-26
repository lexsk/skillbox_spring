package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private final Logger logger = Logger.getLogger(BookRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> retrieveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author", book.getAuthor());
        parameterSource.addValue("title", book.getTitle());
        parameterSource.addValue("size", book.getSize());
        jdbcTemplate.update("INSERT INTO books (author, title, size) VALUES (:author, :title, :size)", parameterSource);
        logger.info("store new book: " + book);
    }

    @Override
    public boolean removeItemById(Integer bookIdToRemove) {
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("id", bookIdToRemove);
            jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
            logger.info("remove book completed");
            return true;
        } catch (Exception e) {
            logger.error("removeItemById ERROR", e);
            return false;
        }
    }

    public boolean removeItemByTitle(String bookTitleToRemove) {
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("title", bookTitleToRemove);
            jdbcTemplate.update("DELETE FROM books WHERE title = :title", parameterSource);
            logger.info("remove book completed");
            return true;
        } catch (Exception e) {
            logger.error("removeItemByTitle ERROR", e);
            return false;
        }
    }

    public boolean removeItemByAuthor(String bookAuthorToRemove) {
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("author", bookAuthorToRemove);
            jdbcTemplate.update("DELETE FROM books WHERE author = :author", parameterSource);
            logger.info("remove book completed");
            return true;
        } catch (Exception e) {
            logger.error("removeItemByAuthor ERROR", e);
            return false;
        }
    }

    public boolean removeItemBySize(Integer bookSizeToRemove) {
        try {
            MapSqlParameterSource parameterSource = new MapSqlParameterSource();
            parameterSource.addValue("size", bookSizeToRemove);
            jdbcTemplate.update("DELETE FROM books WHERE size = :size", parameterSource);
            logger.info("remove book completed");
            return true;
        } catch (Exception e) {
            logger.error("removeItemBySize ERROR", e);
            return false;
        }
    }


    public List<Book> getItemByTitle(String bookTitleForFilter) {
        List<Book> books = new ArrayList<>();
        Pattern pattern = Pattern.compile(bookTitleForFilter);
        for (Book book : retrieveAll()) {
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
        for (Book book : retrieveAll()) {
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
        for (Book book : retrieveAll()) {
            Matcher matcher = pattern.matcher(String.valueOf(book.getSize()));
            if (matcher.find()) {
                books.add(book);
            }
        }
        return books;
    }

}
