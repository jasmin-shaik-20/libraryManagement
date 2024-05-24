package com.example.libraryManagement.dto;

import com.example.libraryManagement.model.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class PublisherRequest {
    private String name;
    private String address;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private List<Book> books;
}
