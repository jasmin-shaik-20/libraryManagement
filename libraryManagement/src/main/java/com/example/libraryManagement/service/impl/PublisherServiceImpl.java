package com.example.libraryManagement.service.impl;

import com.example.libraryManagement.dto.PublisherRequest;
import com.example.libraryManagement.exception.PublisherNotFoundException;
import com.example.libraryManagement.model.Publisher;
import com.example.libraryManagement.repository.PublisherRepository;
import com.example.libraryManagement.service.PublisherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PublisherServiceImpl implements PublisherService {
    @Autowired
    PublisherRepository publisherRepository;

    @Override
    public Publisher createPublisher(PublisherRequest publisherRequest) {
        Publisher publisher = Publisher.build(0, publisherRequest.getName(), publisherRequest.getAddress(), LocalDate.now(), LocalDate.now(), publisherRequest.getBooks());
        return publisherRepository.save(publisher);
    }

    @Override
    public Publisher getPublisherById(int id) throws PublisherNotFoundException {
        Publisher publisher = publisherRepository.findById(id).orElse(null);
        if (publisher == null) {
            throw new PublisherNotFoundException("Publisher not found");
        } else {
            return publisher;
        }
    }

    @Override
    public Publisher updatePublisher(int id, PublisherRequest publisherRequest) throws PublisherNotFoundException {
        Publisher publisher = publisherRepository.findById(id).orElse(null);
        if (publisher != null) {
            publisher.setName(publisherRequest.getName());
            publisher.setAddress(publisherRequest.getAddress());
            publisher.setUpdatedAt(LocalDate.now());
            publisher.setBooks(publisherRequest.getBooks());
            return publisherRepository.save(publisher);
        } else {
            throw new PublisherNotFoundException("Publisher not found");
        }
    }

    @Override
    public String deletePublisher(int id) throws PublisherNotFoundException {
        Publisher publisher = publisherRepository.findById(id).orElse(null);
        if (publisher != null) {
            publisherRepository.deleteById(id);
            return "Publisher deleted";
        } else {
            throw new PublisherNotFoundException("Publisher not found");
        }
    }

    @Override
    public List<Publisher> getAllPublishers() {
        return publisherRepository.findAll();
    }
}
