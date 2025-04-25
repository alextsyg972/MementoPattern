package org.example.practice2.service;

import org.example.practice2.entity.Book;
import org.example.practice2.entity.PageSort;
import org.example.practice2.exception.BookNotFoundException;
import org.example.practice2.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    private BookRepository bookRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    @Override
    public Page<Book> getAllBooks(Pageable pageable, PageSort sort) {
        return bookRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        sort.getSortValue()
                )
        );
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, Book book) {
        Book updatedBook = getBookById(id);
        updatedBook.setAuthor(book.getAuthor());
        updatedBook.setName(book.getName());
        updatedBook.setGenre(book.getGenre());
        updatedBook.setPrice(book.getPrice());
        return bookRepository.save(updatedBook);
    }

    @Override
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
