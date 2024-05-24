package com.example.libraryManagement.service;

import com.example.libraryManagement.dto.PublisherRequest;
import com.example.libraryManagement.exception.PublisherNotFoundException;
import com.example.libraryManagement.model.Publisher;

import java.util.List;

public interface PublisherService {

    public Publisher createPublisher(PublisherRequest publisherRequest);
    public Publisher getPublisherById(int id) throws PublisherNotFoundException;
    public Publisher updatePublisher(int id, PublisherRequest publisherRequest) throws PublisherNotFoundException;
    public String deletePublisher(int id) throws PublisherNotFoundException;
    public List<Publisher> getAllPublishers();
}
