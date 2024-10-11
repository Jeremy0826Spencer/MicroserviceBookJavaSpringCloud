package com.south.clients;

import com.south.models.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "book-service")
//This client is used in InventoryServiceImpl to check for the book
//before creating the inventory
public interface BookServiceClient {

    @GetMapping("/books/{id}")
    BookDto getBookById(@PathVariable("id") Long id);
}
