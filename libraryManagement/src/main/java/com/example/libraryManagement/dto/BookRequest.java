package com.example.libraryManagement.dto;

import com.example.libraryManagement.model.Publisher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
public class BookRequest {
    private String title;
    private String description;
    private String category;
    private LocalDate createdDate;
    private LocalDate updatedDate;
    private Publisher publisher;
}
