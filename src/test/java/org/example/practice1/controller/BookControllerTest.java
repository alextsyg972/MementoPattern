package org.example.practice1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.practice1.entity.Book;
import org.example.practice1.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
        book1.setAuthor("My author1");
        book1.setTitle("my title1");
        book1.setPublicationYear(1998L);
        book1.setId(1L);

        Book book2 = new Book();
        book2.setAuthor("My author2");
        book2.setTitle("my title2");
        book2.setPublicationYear(2000L);
        book2.setId(2L);

        List<Book> list = List.of(book1, book2);
        when(bookService.getAllBooks()).thenReturn(list);

        mockMvc.perform(get("/api/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].title").value("my title1"))
                .andExpect(jsonPath("$.[0].author").value("My author1"))
                .andExpect(jsonPath("$.[0].publicationYear").value(1998))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].title").value("my title2"))
                .andExpect(jsonPath("$.[1].author").value("My author2"))
                .andExpect(jsonPath("$.[1].publicationYear").value(2000));
        verify(bookService, times(1)).getAllBooks();

    }

    @Test
    void getBookById() throws Exception {
        Book book1 = new Book();
        book1.setAuthor("My author1");
        book1.setTitle("my title1");
        book1.setPublicationYear(1998L);
        book1.setId(1L);

        when(bookService.getBookById(1L)).thenReturn(book1);


        mockMvc.perform(get("/api/v1/books/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("my title1"))
                .andExpect(jsonPath("$.author").value("My author1"))
                .andExpect(jsonPath("$.publicationYear").value(1998));
        verify(bookService, times(1)).getBookById(1L);
    }

    @Test
    void createBook() throws Exception {
        Book book1 = new Book();
        book1.setAuthor("My author1");
        book1.setTitle("my title1");
        book1.setPublicationYear(1998L);
        book1.setId(1L);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isCreated());

        verify(bookService, times(1)).createBook(book1);
    }

    @Test
    void updateBook() throws Exception {
        Book book1 = new Book();
        book1.setAuthor("My author1");
        book1.setTitle("my title1");
        book1.setPublicationYear(1998L);

        mockMvc.perform(put("/api/v1/books/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(status().isOk());

        verify(bookService, times(1)).updateBook(1L, book1);


    }

    @Test
    void deleteBookById() throws Exception {
        mockMvc.perform(delete("/api/v1/books/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(bookService, times(1)).deleteBookById(1L);
    }
}