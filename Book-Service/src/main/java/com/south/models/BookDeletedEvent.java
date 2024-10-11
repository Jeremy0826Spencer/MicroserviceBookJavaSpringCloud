package com.south.models;

public class BookDeletedEvent {
    private Long bookId;

    public BookDeletedEvent() {}

    public BookDeletedEvent(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}