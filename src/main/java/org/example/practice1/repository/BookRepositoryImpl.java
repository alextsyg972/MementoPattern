package org.example.practice1.repository;

import org.example.practice1.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    @Override
    public Book findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM book WHERE id = ?", new BeanPropertyRowMapper<>(Book.class), id);
    }

    @Override
    public int save(Book book) {
        return jdbcTemplate.update("INSERT INTO book(title, author, publicationYear) VALUES (?,?,?)", book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    @Override
    public int update(Long id, Book book) {
        return jdbcTemplate.update("UPDATE book SET title=?, author=?, publicationYear=? WHERE id=?", book.getTitle(), book.getAuthor(), book.getPublicationYear());
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }
}
