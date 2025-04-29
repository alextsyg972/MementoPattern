package org.example.practice1.service;

import org.example.practice1.entity.Book;
import org.example.practice1.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllBooks() {
        Book book1 = new Book();
        book1.setAuthor("My author1");
        book1.setTitle("my title1");
        book1.setPublicationYear(1998L);
        book1.setId(1L);

        Book book2 = new Book();
        book2.setAuthor("My author2");
        book2.setTitle("my title2");
        book2.setPublicationYear(2000L);
        book2.setId(2L);
        List<Book> bookList = List.of(book1, book2);

        when(bookRepository.findAll()).thenReturn(bookList);

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        verify(bookRepository, times(1)).findAll();

    }

    @Test
    void getBookById() {
        Book book1 = new Book();
        book1.setAuthor("My author1");
        book1.setTitle("my title1");
        book1.setPublicationYear(1998L);
        book1.setId(1L);

        when(bookRepository.findById(1L)).thenReturn(book1);

        Book result = bookService.getBookById(1L);

        assertEquals(1L, result.getId());
        assertEquals("my title1", result.getTitle());
        verify(bookRepository, times(1)).findById(1L);

    }

    @Test
    void createBook() {
        Book book1 = new Book();
        book1.setAuthor("My author1");
        book1.setTitle("my title1");
        book1.setPublicationYear(1998L);
        book1.setId(1L);

        when(bookRepository.save(book1)).thenReturn(1);

        Integer result = bookRepository.save(book1);

        assertEquals(1, result);
        verify(bookRepository, times(1)).save(book1);


    }

    @Test
    void updateBook() {
        Book book1 = new Book();
        book1.setAuthor("My author1");
        book1.setTitle("my title1");
        book1.setPublicationYear(1998L);
        book1.setId(1L);


        when(bookRepository.update(1L, book1)).thenReturn(1);

        Integer result = bookService.updateBook(1L, book1);

        assertEquals(1, result);
        verify(bookRepository, times(1)).update(1L,book1);

    }

    @Test
    void deleteBookById() {
        bookService.deleteBookById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}