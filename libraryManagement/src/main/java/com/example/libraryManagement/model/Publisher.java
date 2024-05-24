package com.example.libraryManagement.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="publisher")
@NoArgsConstructor
@AllArgsConstructor(staticName = "build")
@Data
public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String address;
    @Column(name="created_date")
    private LocalDate createdDate;
    @Column(name="updated_date")
    private LocalDate updatedAt;

    @OneToMany(targetEntity = Book.class,cascade = CascadeType.ALL)
    @JoinColumn(name="publisher_id", referencedColumnName = "id")
    @JsonManagedReference
    private List<Book> books;

    public void addBook(Book book) {
        books.add(book);
        book.setPublisher(this);
    }
}
