package com.example.libraryManagement.controller;

import com.example.libraryManagement.dto.PublisherRequest;
import com.example.libraryManagement.exception.PublisherNotFoundException;
import com.example.libraryManagement.model.Book;
import com.example.libraryManagement.model.Publisher;
import com.example.libraryManagement.service.PublisherService;
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

@WebMvcTest(PublisherController.class)
public class PublisherControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PublisherService publisherService;
    Publisher publisher1;
    Publisher publisher2;
    PublisherRequest publisherRequest1;
    PublisherRequest publisherRequest2;
    private Book book1;
    private Book book2;
    private List<Publisher> publisherList;
    private List<Book> bookList;


    @BeforeEach
    void setUp(){
        book1 = Book.build(0, "a", "b", "c", LocalDate.now(), LocalDate.now(), null);
        book2 = Book.build(1, "b", "c", "d", LocalDate.now(), LocalDate.now(), null);
        bookList=List.of(book1,book2);
        publisher1 = Publisher.build(0, "a", "b", LocalDate.now(), LocalDate.now(), bookList);
        publisherRequest1 = PublisherRequest.build("a", "b", LocalDate.now(), LocalDate.now(), bookList);
        publisher2 = Publisher.build(1, "a", "b", LocalDate.now(), LocalDate.now(), bookList);
        publisherList=List.of(publisher1,publisher2);
        publisherRequest2 = PublisherRequest.build("a", "b", LocalDate.now(), LocalDate.now(), bookList);
    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void createPublisherDetails() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(publisherRequest1);
        when(publisherService.createPublisher(publisherRequest1)).thenReturn(publisher1);
        this.mockMvc.perform(post("/publisher")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getPublisherById() throws Exception {
        when(publisherService.getPublisherById(0)).thenReturn(publisher1);
        this.mockMvc.perform(get("/publisher/" + 0 ))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void getAllPublisherDetails() throws Exception {
        when(publisherService.getAllPublishers()).thenReturn(publisherList);
        this.mockMvc.perform(get("/publisher/all"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void updatePublisherDetails() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(publisherRequest1);
        when(publisherService.createPublisher(publisherRequest1)).thenReturn(publisher1);
        this.mockMvc.perform(put("/publisher/" + 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deletePublisherDetails() throws Exception {
        when(publisherService.deletePublisher(0))
                .thenReturn("Publisher deleted");
        this.mockMvc.perform(delete("/publisher/" + 0))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    void deletePublisherByIdException() throws Exception{
        when(publisherService.deletePublisher(2)).thenThrow(new PublisherNotFoundException("Publisher not found"));
        this.mockMvc.perform(delete("/publisher/" + 2))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

    @Test
    void getPublisherByIdException() throws Exception {
        when(publisherService.deletePublisher(2)).thenThrow(new PublisherNotFoundException("Publisher not found"));
        this.mockMvc.perform(delete("/publisher/" + 2))
                .andDo(print()).andExpect(status().isInternalServerError());
    }

}




