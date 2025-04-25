package org.example.practice2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.practice2.entity.Author;
import org.example.practice2.entity.Book;
import org.example.practice2.entity.Genre;
import org.example.practice2.entity.PageSort;
import org.example.practice2.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void getAllBooks() throws Exception {
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(200));
        book1.setGenre(Genre.HORROR);
        book1.setName("My book");
        book1.setAuthor(new Author(1L, "Alex Tsyg"));
        book1.setId(1L);

        Book book2 = new Book();
        book2.setPrice(BigDecimal.valueOf(500));
        book2.setGenre(Genre.FANTASY);
        book2.setName("New Book");
        book2.setAuthor(new Author(2L, "Mikhail Es"));
        book2.setId(2L);


        PageSort pageSort = PageSort.ID_ASC;
        Pageable pageable = PageRequest.of(0, 2, pageSort.getSortValue());

        Page<Book> page = new PageImpl<>(List.of(book1, book2), pageable, 2);
        when(bookService.getAllBooks(pageable, pageSort)).thenReturn(page);

        mockMvc.perform(get("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBookById() throws Exception {
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(200));
        book1.setGenre(Genre.HORROR);
        book1.setName("My book");
        book1.setAuthor(new Author(1L, "Alex Tsyg"));
        book1.setId(1L);

        when(bookService.getBookById(1L)).thenReturn(book1);

        mockMvc.perform(get("/api/v1/books/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("My book"))
                .andExpect(jsonPath("$.genre").value("HORROR"))
                .andExpect(jsonPath("$.price").value(200))
                .andExpect(jsonPath("$.author.id").value(1))
                .andExpect(jsonPath("$.author.name").value("Alex Tsyg"));
        verify(bookService, times(1)).getBookById(1L);


    }

    @Test
    void createBook() throws Exception {
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(200));
        book1.setGenre(Genre.HORROR);
        book1.setName("My book");
        book1.setAuthor(new Author(1L, "Alex Tsyg"));
        book1.setId(1L);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isCreated());

    }

    @Test
    void updateBook() throws Exception {
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(200));
        book1.setGenre(Genre.HORROR);
        book1.setName("My book");
        book1.setAuthor(new Author(1L, "Alex Tsyg"));
        book1.setId(1L);

        Book book2 = new Book();
        book2.setPrice(BigDecimal.valueOf(500));
        book2.setGenre(Genre.FANTASY);
        book2.setName("New Book");
        book2.setAuthor(new Author(2L, "Mikhail Es"));
        book2.setId(2L);

        when(bookService.updateBook(1L, book1)).thenReturn(book2);


        mockMvc.perform(put("/api/v1/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("New Book"));

        verify(bookService, times(1)).updateBook(1L, book1);

    }

    @Test
    void deleteBookById() throws Exception {
        mockMvc.perform(delete("/api/v1/books/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBookById(1L);
    }
}