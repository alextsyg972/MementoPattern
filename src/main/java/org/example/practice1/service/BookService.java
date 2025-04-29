package org.example.practice1.service;

import org.example.practice1.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    Book getBookById(Long id);

    Integer createBook(Book book);

    Integer updateBook(Long id, Book book);

    void deleteBookById(Long id);

}
