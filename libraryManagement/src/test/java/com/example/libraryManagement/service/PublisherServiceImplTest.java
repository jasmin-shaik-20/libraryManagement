package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.PublisherRequest;
import com.example.libraryManagement.exception.PublisherNotFoundException;
import com.example.libraryManagement.model.Book;
import com.example.libraryManagement.model.Publisher;
import com.example.libraryManagement.repository.PublisherRepository;
import com.example.libraryManagement.service.impl.PublisherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PublisherServiceImplTest {

    @InjectMocks
    private PublisherServiceImpl publisherService;
    @Mock
    private PublisherRepository publisherRepository;
    private Publisher publisher;
    private PublisherRequest publisherRequest;
    private List<Book> bookList;
    private Book book;

    @BeforeEach
    void setUp() {
        book = Book.build(0, "a", "b", "c", LocalDate.now(), LocalDate.now(), null);
        bookList = List.of(book);
        publisher = Publisher.build(0, "a", "b", LocalDate.now(), LocalDate.now(), bookList);
        publisherRequest = PublisherRequest.build("a", "b", LocalDate.now(), LocalDate.now(), bookList);
    }


    @Test
    void testCreatePublisher() {
        when(publisherRepository.save(publisher)).thenReturn(publisher);
        assertThat(publisherService.createPublisher(publisherRequest)).isEqualTo(publisher);
    }

    @Test
    void testUpdatePublisher() throws PublisherNotFoundException {
        when(publisherRepository.findById(0)).thenReturn(Optional.ofNullable(publisher));
        when(publisherRepository.save(publisher)).thenReturn(publisher);
        assertThat(publisherService.updatePublisher(0, publisherRequest)).isEqualTo(publisher);
    }

    @Test
    void testGetPublisherById() throws PublisherNotFoundException {
        when(publisherRepository.findById(0)).thenReturn(Optional.ofNullable(publisher));
        assertThat(publisherService.getPublisherById(0)).isEqualTo(publisher);
    }

    @Test
    void testGetPublisherByIdException(){
        assertThrows(PublisherNotFoundException.class,() ->{
            publisherService.getPublisherById(0);
        });
    }

    @Test
    void testGetAllPublishers() {
        when(publisherRepository.findAll()).thenReturn(new ArrayList<Publisher>(Collections.singleton(publisher)));
        assertThat(publisherService.getAllPublishers().get(0).getName()).isEqualTo(publisher.getName());
    }

    @Test
    void testDeletePublisher() throws Exception {
        when(publisherRepository.findById(0)).thenReturn(Optional.ofNullable(publisher));
        doNothing().when(publisherRepository).deleteById(0);
        assertThat(publisherService.deletePublisher(0)).isEqualTo("Publisher deleted");
    }
    @Test
    void testDeletePublisherByIdException(){
        assertThrows(PublisherNotFoundException.class,() ->{
            publisherService.deletePublisher(0);
        });
    }

}
