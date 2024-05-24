package com.example.libraryManagement.controller;

import com.example.libraryManagement.dto.BookRequest;
import com.example.libraryManagement.exception.BookNotFoundException;
import com.example.libraryManagement.exception.PublisherNotFoundException;
import com.example.libraryManagement.model.Book;
import com.example.libraryManagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    BookService bookService;

    @PostMapping
    public Book createBook(@RequestBody @Valid BookRequest bookRequest){
        return bookService.createBook(bookRequest);
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable("id") int id) throws BookNotFoundException {
        return bookService.getBookById(id);
    }

    @GetMapping("publisherId/{id}")
    public List<Book> getBookByPublisherId(@PathVariable("id") int id) throws PublisherNotFoundException {
        return bookService.getBookByPublisherId(id);
    }

    @PutMapping("{id}")
    public Book updateBook(@PathVariable("id") int id, @RequestBody @Valid BookRequest bookRequest) throws BookNotFoundException {
        return bookService.updateBook(id, bookRequest);
    }

    @DeleteMapping("{id}")
    public String deleteBook(@PathVariable("id") int id) throws BookNotFoundException {
        return bookService.deleteBook(id);
    }

    @GetMapping("/all")
    public List<Book> getAllBooks()  {
        return bookService.getAllBooks();
    }
}
