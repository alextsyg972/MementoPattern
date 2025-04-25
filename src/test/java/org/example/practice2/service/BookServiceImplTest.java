package org.example.practice2.service;

import org.example.practice2.entity.Book;
import org.example.practice2.entity.PageSort;
import org.example.practice2.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        Book book = new Book();
        book.setName("My book");

        PageSort pageSort = PageSort.ID_ASC;
        Pageable pageable = PageRequest.of(0, 2, pageSort.getSortValue());

        Page<Book> page = new PageImpl<>(List.of(book), pageable, 1 );
        when(bookRepository.findAll(pageable)).thenReturn(page);

        Page<Book> result = bookService.getAllBooks(pageable, PageSort.ID_ASC);
        assertEquals(page.get().findFirst().get(), result.get().findFirst().get());

    }

    @Test
    void getBookById() {

        Book book = new Book();
        book.setName("My book");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);
        assertEquals("My book", result.getName());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void createBook() {
        Book book = new Book();
        book.setName("My book");
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.createBook(book);
        assertEquals(book, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void updateBook() {
        Book book = new Book();
        book.setName("My book");
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookService.createBook(book);
        assertEquals(book, result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteBookById() {
        bookService.deleteBookById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}