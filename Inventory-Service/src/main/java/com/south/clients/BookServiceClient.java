package com.south.clients;

import com.south.models.BookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "book-service")
public interface BookServiceClient {

    Logger logger = LoggerFactory.getLogger(BookServiceClient.class);

    @GetMapping("/books/{id}")
    default BookDto getBookById(@PathVariable("id") Long id) {
        logger.info("Fetching book with ID: {}", id);
        return getBookByIdInternal(id);
    }

    @GetMapping("/books/{id}")
    BookDto getBookByIdInternal(@PathVariable("id") Long id);
}