package com.renting.book.service;

import com.renting.book.entity.BookEntity;
import com.renting.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookEntity> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookEntity getBookById(Long id) {
        Optional<BookEntity> bookEntity = bookRepository.findById(id);
        return bookEntity.orElse(null);
    }

    @Override
    public BookEntity saveBook(BookEntity bookEntity) {
        return bookRepository.save(bookEntity);
    }

    @Override
    public BookEntity updateBook(Long id, BookEntity updateBook) {
        Optional<BookEntity> existingBookOptional = bookRepository.findById(id);
        if (existingBookOptional.isPresent()) {
            BookEntity existingBook = existingBookOptional.get();
            existingBook.setTitle(updateBook.getTitle());
            existingBook.setPlace(updateBook.getPlace());
            existingBook.setRented(updateBook.isRented());
            if (updateBook.isRented()) {
                existingBook.setName(updateBook.getName());
                existingBook.setReturnDate(updateBook.getReturnDate());
            } else {
                existingBook.setName(null);
                existingBook.setReturnDate(null);
            }
            return bookRepository.save(existingBook);
        } else {
            return null;
        }
    }

    @Override
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<BookEntity> getRentedBooks() {
        return bookRepository.findByIsRented(true);
    }
}
