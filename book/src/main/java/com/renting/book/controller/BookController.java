package com.renting.book.controller;

import com.renting.book.entity.BookEntity;
import com.renting.book.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
    public String getAllBooks(Model model) {
        List<BookEntity> books = bookService.getAllBooks();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/add")
    public String addBookForm(Model model) {
        model.addAttribute("book", new BookEntity());
        return "addBook";
    }

    @PostMapping("/add")
    public String saveBook(@ModelAttribute("book") BookEntity bookEntity) {
        bookService.saveBook(bookEntity);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String editBookForm(@PathVariable Long id, Model model) {
        BookEntity bookEntity = bookService.getBookById(id);
        if (bookEntity != null) {
            model.addAttribute("book", bookEntity);
            return "editBook";
        } else {
            return "redirect:/books";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable Long id, @ModelAttribute("book") BookEntity bookEntity) {
        bookService.updateBook(id, bookEntity);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/rented")
    public String getRentedBooks(Model model) {
        List<BookEntity> books = bookService.getRentedBooks();
        model.addAttribute("books", books);
        return "rentedBooks";
    }

    @GetMapping("/{id}")
    public String getBookById(@PathVariable Long id, Model model) {
        BookEntity bookEntity = bookService.getBookById(id);
        if (bookEntity != null) {
            model.addAttribute("book", bookEntity);
            return "bookById";
        } else {
            return "redirect:/books";
        }
    }

    @GetMapping("/api/{id}")
    public ResponseEntity<BookEntity> getBookByIdApi(@PathVariable Long id) {
        BookEntity bookEntity = bookService.getBookById(id);
        return bookEntity != null ? ResponseEntity.ok(bookEntity) :
                ResponseEntity.notFound().build();
    }

    @PutMapping("/api/{id}")
    public ResponseEntity<BookEntity> updateBookApi(
            @PathVariable Long id, @RequestBody BookEntity updateBook) {
        BookEntity updatedBook = bookService.updateBook(id, updateBook);
        return updatedBook != null ? ResponseEntity.ok(updatedBook) :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/api/{id}")
    public ResponseEntity<Void> deleteBookApi(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/rented")
    public List<BookEntity> getRentedBooksApi() {
        return bookService.getRentedBooks();
    }
}
