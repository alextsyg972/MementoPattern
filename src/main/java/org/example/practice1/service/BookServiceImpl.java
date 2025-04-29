package org.example.practice1.service;

import org.example.practice1.entity.Book;
import org.example.practice1.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    @Override
    public Integer createBook(Book book) {
        return bookRepository.save(book);
    }

    @Transactional
    @Override
    public Integer updateBook(Long id, Book book) {
        return bookRepository.update(id, book);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
