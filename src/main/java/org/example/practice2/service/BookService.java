package org.example.practice2.service;

import org.example.practice2.entity.Book;
import org.example.practice2.entity.PageSort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookService {

    Page<Book> getAllBooks(Pageable pageable, PageSort sort);

    Book getBookById(Long id);

    Book createBook(Book book);

    Book updateBook(Long id, Book book);

    void deleteBookById(Long id);

}
