package com.example.libraryManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Data
@Table(name="book")
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String title;
    private String description;
    private String category;
    @Column(name="created_date")
    private LocalDate createdAt;
    @Column(name="updated_date")
    private LocalDate updatedAt;
    @ManyToOne
    @JoinColumn(name="publisher_id")
    @JsonBackReference
    private Publisher publisher;
}
