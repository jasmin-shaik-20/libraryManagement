package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.BookRequest;
import com.example.libraryManagement.exception.BookNotFoundException;
import com.example.libraryManagement.model.Book;
import com.example.libraryManagement.repository.BookRepository;
import com.example.libraryManagement.service.impl.BookServiceImpl;
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
public class BookServiceImplTest {
    @InjectMocks
    private BookServiceImpl bookService;
    @Mock
    private BookRepository bookRepository;

    private Book book;
    private List<Book> bookList;

    private BookRequest bookRequest;

    @BeforeEach
    void setUp() {
        book = Book.build(0, "cake", "sweet", "bakery", LocalDate.now(), LocalDate.now(), null);
        bookList=List.of(book);
        bookRequest = BookRequest.build("cake", "sweet", "bakery", LocalDate.now(), LocalDate.now(), null);
    }

    @Test
    void testCreateBook() {
        when(bookRepository.save(book)).thenReturn(book);
        assertThat(bookService.createBook(bookRequest)).isEqualTo(book);
    }

    @Test
    void testUpdateBook() throws BookNotFoundException {
        when(bookRepository.findById(0)).thenReturn(Optional.ofNullable(book));
        when(bookRepository.save(book)).thenReturn(book);
        assertThat(bookService.updateBook(0,bookRequest)).isEqualTo(book);
    }

    @Test
    void testGetBookById() throws BookNotFoundException{
        when(bookRepository.findById(0)).thenReturn(Optional.ofNullable(book));
        assertThat(bookService.getBookById(0)).isEqualTo(book);
    }

    @Test
    void testGetAllBook(){
        when(bookRepository.findAll()).thenReturn(new ArrayList<Book>(Collections.singleton(book)));
        assertThat(bookService.getAllBooks().get(0).getTitle()).isEqualTo(book.getTitle());
    }

    @Test
    void testDeleteBook() throws Exception {
        when(bookRepository.findById(0)).thenReturn(Optional.ofNullable(book));
        doNothing().when(bookRepository).deleteById(0);
        assertThat(bookService.deleteBook(0)).isEqualTo("Book deleted Successfully");
    }

    @Test
    void testDeleteBookByIdException(){
        assertThrows(BookNotFoundException.class,() ->{
            bookService.deleteBook(0);
        });
    }

    @Test
    void testGetBookByIdException(){
        assertThrows(BookNotFoundException.class,() ->{
            bookService.getBookById(0);
        });
    }


}
