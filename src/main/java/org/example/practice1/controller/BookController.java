package org.example.practice1.controller;

import org.example.practice1.entity.Book;
import org.example.practice1.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {


    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<>(bookService.getAllBooks(), HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PostMapping("/books")
    public ResponseEntity<HttpStatus> createBook(@RequestBody Book book) {
        bookService.createBook(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<HttpStatus> updateBook(@PathVariable Long id, @RequestBody Book book) {
        bookService.updateBook(id, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<HttpStatus> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
