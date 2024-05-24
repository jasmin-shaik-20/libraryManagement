package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.BookRequest;
import com.example.libraryManagement.exception.BookNotFoundException;
import com.example.libraryManagement.exception.PublisherNotFoundException;
import com.example.libraryManagement.model.Book;


import java.util.List;

public interface BookService {
    public Book createBook(BookRequest bookRequest);
    public Book getBookById(int id) throws BookNotFoundException;
    public Book updateBook(int id, BookRequest bookRequest) throws BookNotFoundException;
    public List<Book> getBookByPublisherId(int id) throws PublisherNotFoundException;
    public String deleteBook(int id) throws BookNotFoundException;
    public List<Book> getAllBooks();
}
