package com.south.services;

import com.south.exceptions.ResourceNotFoundException;
import com.south.models.Book;
import com.south.models.BookDeletedEvent;
import com.south.repositories.BookRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
    private final BookRepository bookRepository;
    private final StreamBridge streamBridge;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, StreamBridge streamBridge) {
        this.bookRepository = bookRepository;
        this.streamBridge = streamBridge;
    }

    @Override
    public Book createBook(Book book) {
        logger.info("Creating new book with ISBN: {}", book.getIsbn());
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long id) {
        logger.info("Fetching book with ID: {}", id);
        return bookRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Book not found with ID: {}", id);
                    return new ResourceNotFoundException("Book not found with id " + id);
                });
    }

    @Override
    public List<Book> getAllBooks() {
        logger.info("Fetching all books");
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Long id, Book bookDetails) {
        logger.info("Updating book with ID: {}", id);
        Book book = getBookById(id);
        // Update fields
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        // ... (update other fields)
        return bookRepository.save(book);
    }

    @Transactional
    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        Book book = getBookById(id);
        bookRepository.delete(book);

        logger.info("Publishing book deletion event to Kafka for Book ID: {}", book.getId());
        BookDeletedEvent event = new BookDeletedEvent(book.getId());
        streamBridge.send("bookDeleted-out-0", event);
    }
}

