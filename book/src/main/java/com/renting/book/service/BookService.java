package com.renting.book.service;

import com.renting.book.entity.BookEntity;

import java.util.List;
public interface BookService {
    List<BookEntity> getAllBooks();
    BookEntity getBookById(Long id);
    BookEntity saveBook(BookEntity bookEntity);
    BookEntity updateBook(Long id, BookEntity updateBook);
    boolean deleteBook(Long id);
    List<BookEntity> getRentedBooks();
}
