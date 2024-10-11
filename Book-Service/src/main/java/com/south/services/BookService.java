package com.south.services;

import com.south.models.Book;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(Long id);
    List<Book> getAllBooks();
    Book updateBook(Long id, Book bookDetails);
    void deleteBook(Long id);
}

