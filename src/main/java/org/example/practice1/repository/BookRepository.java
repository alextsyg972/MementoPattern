package org.example.practice1.repository;

import org.example.practice1.entity.Book;

import java.util.List;

public interface BookRepository {

    List<Book> findAll();

    Book findById(Long id);

    int save(Book book);

    int update(Long id,Book book);

    void deleteById(Long id);

}
