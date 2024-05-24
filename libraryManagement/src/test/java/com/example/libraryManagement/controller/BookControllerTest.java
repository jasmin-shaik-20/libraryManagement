package com.example.libraryManagement.controller;

import com.example.libraryManagement.dto.BookRequest;
import com.example.libraryManagement.exception.BookNotFoundException;
import com.example.libraryManagement.model.Book;
import com.example.libraryManagement.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BookService bookService;
    private Book book1;
    private Book book2;
    private BookRequest bookRequest1;
    private BookRequest bookRequest2;
    private List<Book> bookList;

    @BeforeEach
    public void setUp() {
        book1 = Book.build(0, "a", "b", "c", LocalDate.now(), LocalDate.now(), null);
        book2 = Book.build(1, "b", "c", "d", LocalDate.now(), LocalDate.now(), null);
        bookList = List.of(book1, book2);
        bookRequest1 = BookRequest.build("a", "b", "c", LocalDate.now(), LocalDate.now(), null);
        bookRequest2 = BookRequest.build("b", "c", "d", LocalDate.now(), LocalDate.now(), null);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void createBook() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(bookRequest1);
        when(bookService.createBook(bookRequest1)).thenReturn(book1);
        this.mockMvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updateBook() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(bookRequest2);
        when(bookService.updateBook(1, bookRequest2)).thenReturn(book2);
        this.mockMvc.perform(put("/book/1")
                       .contentType(MediaType.APPLICATION_JSON)
                       .content(requestJson))
               .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getBookById() throws Exception {
        when(bookService.getBookById(0)).thenReturn(book1);
        this.mockMvc.perform(get("/book/" + 0 ))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAllBookDetails() throws Exception {
        when(bookService.getAllBooks()).thenReturn(bookList);
        this.mockMvc.perform(get("/book/all"))
               .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deleteBook() throws Exception {
        when(bookService.deleteBook(0)).thenReturn("Book deleted Successfully");
        this.mockMvc.perform(get("/book/" + 0))
               .andDo(print()).andExpect(status().isOk());
    }
    //failure
    @Test
    void deleteBookByIdException() throws Exception{
        when(bookService.deleteBook(2)).thenThrow(new BookNotFoundException("Book not found with 2"));
        this.mockMvc.perform(delete("/book/" + 2))
               .andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    void getBookByIdException() throws Exception{
        when(bookService.getBookById(2)).thenThrow(new BookNotFoundException("Book not found with 2"));
        this.mockMvc.perform(get("/book/" + 2))
               .andDo(print()).andExpect(status().isInternalServerError());
    }

}







