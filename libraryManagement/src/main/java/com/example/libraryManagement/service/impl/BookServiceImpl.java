package com.example.libraryManagement.service.impl;

import com.example.libraryManagement.dto.BookRequest;
import com.example.libraryManagement.exception.BookNotFoundException;
import com.example.libraryManagement.exception.PublisherNotFoundException;
import com.example.libraryManagement.model.Book;
import com.example.libraryManagement.model.Publisher;
import com.example.libraryManagement.repository.BookRepository;
import com.example.libraryManagement.repository.PublisherRepository;
import com.example.libraryManagement.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    @Autowired
    BookRepository bookRepository;
    @Autowired
    PublisherRepository publisherRepository;

    @Override
    public Book createBook(BookRequest bookRequest) {
        Book book = Book.build(0,bookRequest.getTitle(),
                bookRequest.getDescription(),
                bookRequest.getCategory(),
                LocalDate.now(),
                LocalDate.now(),bookRequest.getPublisher());
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(int id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElse(null);
        if(book!=null){
            return book;
        }
        else{
            throw new BookNotFoundException("Book not found with id: "+id);
        }
    }

    @Override
    public Book updateBook(int id, BookRequest bookRequest) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElse(null);
        if(book!=null){
            book.setTitle(bookRequest.getTitle());
            book.setDescription(bookRequest.getDescription());
            book.setCategory(bookRequest.getCategory());
            book.setUpdatedAt(bookRequest.getUpdatedDate());
            return bookRepository.save(book);
        }else{
            throw new BookNotFoundException("Book not found with id: "+id);
        }
    }

    @Override
    public List<Book> getBookByPublisherId(int id) throws PublisherNotFoundException {
        Publisher publisher=publisherRepository.findById(id).orElse(null);
        if(publisher!=null){
            return publisher.getBooks();
        }else{
            throw new PublisherNotFoundException("Publisher not found with id: "+id);
        }
    }

    @Override
    public String deleteBook(int id) throws BookNotFoundException {
        Book book = bookRepository.findById(id).orElse(null);
        if(book!=null){
            bookRepository.deleteById(id);
            return "Book deleted Successfully";
        }else{
            throw new BookNotFoundException("Book not found with id: "+id);
        }
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
}
