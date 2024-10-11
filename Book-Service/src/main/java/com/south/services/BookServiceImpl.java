package com.south.services;

import com.south.exceptions.ResourceNotFoundException;
import com.south.models.Book;
import com.south.models.BookDeletedEvent;
import com.south.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final StreamBridge streamBridge;
    @Autowired
    public BookServiceImpl(BookRepository bookRepository, StreamBridge streamBridge) {
        this.bookRepository = bookRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        // Update fields
        book.setTitle(bookDetails.getTitle());
        // ... (update other fields)
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        // Fetch the book by ID
        Book book = getBookById(id);

        // Delete the book from the repository
        bookRepository.delete(book);

        // Publish BookDeletedEvent to Kafka
        BookDeletedEvent event = new BookDeletedEvent(book.getId());
        streamBridge.send("bookDeleted-out-0", event);
    }



}

