package org.example.practice2.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.practice2.controller.BookController;
import org.example.practice2.entity.Author;
import org.example.practice2.entity.Book;
import org.example.practice2.entity.Genre;
import org.example.practice2.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WebMvcTest(BookController.class)
class BookExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void handleBookNotFound() throws Exception {
        when(bookService.getBookById(1L)).thenThrow(new BookNotFoundException("book with this id not found"));

        mockMvc.perform(get("/api/v1/books/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("book with this id not found"));

    }

    @Test
    void handleWrongJson() {
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(200));
        book1.setGenre(Genre.HORROR);
        book1.setName("My book");
        book1.setAuthor(new Author(1L, "Alex Tsyg"));
        book1.setId(1L);


    }

    @Test
    void handleMethodArgumentNotValid() throws Exception {
        Book book1 = new Book();
        book1.setPrice(BigDecimal.valueOf(200));
        book1.setGenre(Genre.HORROR);
        book1.setName("");
        book1.setAuthor(new Author(1L, "Alex Tsyg"));
        book1.setId(1L);

        mockMvc.perform(post("/api/v1/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(book1)))
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors.name").value("must not be blank"))
                .andExpect(status().isBadRequest());


    }
}