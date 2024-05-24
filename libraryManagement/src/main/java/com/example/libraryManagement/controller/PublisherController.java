package com.example.libraryManagement.controller;

import com.example.libraryManagement.dto.PublisherRequest;
import com.example.libraryManagement.exception.PublisherNotFoundException;
import com.example.libraryManagement.model.Publisher;
import com.example.libraryManagement.service.PublisherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publisher")
public class PublisherController {
    @Autowired
    PublisherService publisherService;

    @PostMapping
    public Publisher createPublisher(@RequestBody @Valid PublisherRequest publisherRequest){
        return publisherService.createPublisher(publisherRequest);
    }

    @GetMapping("{id}")
    public Publisher getPublisherById(@PathVariable("id") int id) throws PublisherNotFoundException {
        return publisherService.getPublisherById(id);
    }

    @PutMapping("{id}")
    public Publisher updatePublisher(@PathVariable("id") int id, @RequestBody @Valid PublisherRequest publisherRequest) throws PublisherNotFoundException {
        return publisherService.updatePublisher(id, publisherRequest);
    }

    @DeleteMapping("{id}")
    public String deletePublisher(@PathVariable("id") int id) throws PublisherNotFoundException {
        return publisherService.deletePublisher(id);
    }

    @GetMapping("/all")
    public List<Publisher> getAllPublisher(){
        return publisherService.getAllPublishers();
    }
}
