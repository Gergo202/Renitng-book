package com.renting.book.controller;

import com.renting.book.entity.BookEntity;
import com.renting.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }
    @GetMapping
    public List<BookEntity> getAllBooks() {
        return bookService.getAllBooks();
    }
    @GetMapping("/{id}")
    public ResponseEntity<BookEntity> getBookById(@PathVariable Long id) {
        BookEntity bookEntity = bookService.getBookById(id);
        return bookEntity != null ? ResponseEntity.ok(bookEntity):
            ResponseEntity.notFound().build();
    }
    @PostMapping("/add")
    public BookEntity saveBook(@RequestBody BookEntity bookEntity) {
        return bookService.saveBook(bookEntity);
    }
    @PutMapping("/edit/{id}")
    public ResponseEntity<BookEntity> updateBook(
            @PathVariable Long id, @RequestBody BookEntity updateBook) {
        bookService.updateBook(id, updateBook);
        return updateBook != null ? ResponseEntity.ok(updateBook) :
                ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/rented")
    public List<BookEntity> getRentedBooks() {
        return bookService.getRentedBooks();
    }
}
