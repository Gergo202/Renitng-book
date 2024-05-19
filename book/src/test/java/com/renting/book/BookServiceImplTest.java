package com.renting.book;

import com.renting.book.entity.BookEntity;
import com.renting.book.repository.BookRepository;
import com.renting.book.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @InjectMocks
    private BookServiceImpl bookService;
    private SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd");
    BookEntity book1;
    BookEntity book2;
    BookEntity updatedBook;
    private Date parseDate(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @BeforeEach
    void setup() throws ParseException {
        MockitoAnnotations.openMocks(this);
        book1 = new BookEntity(1L, "Book 1", false,
                "A101", null, null);
        book2 = new BookEntity(2L, "Book 2", true,
                "A102", "User", dateFormat.parse("2024-06-01"));
        updatedBook = new BookEntity(1L, "BookUpdatedName", true,
                "B100", "UserUp", dateFormat.parse("2024-06-01"));
    }
    @Test
    public void testGetAllBooks() {
        when(bookRepository.findAll()).
                thenReturn(Arrays.asList(book1, book2));

        List<BookEntity> book1 =
                bookService.getAllBooks();
        List<BookEntity> book2 =
                bookService.getAllBooks();

        assertEquals(2, book1.size());
        assertEquals(2, book2.size());
        verify(bookRepository, times(2)).findAll();
    }

    @Test
    public void testGetBookById() {
        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book1));

        BookEntity foundBook = bookService.getBookById(1L);

        assertEquals(book1, foundBook);
        verify(bookRepository, times(1))
                .findById(1L);
    }
    @Test
    public void testSaveBook(){
        when(bookRepository.save(any(BookEntity.class)))
                .thenReturn(book1);

        BookEntity savedBook = bookService.saveBook(book1);

        assertEquals(book1, savedBook);
        verify(bookRepository, times(1))
                .save(book1);
    }

    @Test
    public void testUpdateBook() {
        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book1));
        when(bookRepository.save(any(BookEntity.class)))
                .thenReturn(updatedBook);

        BookEntity result = bookService.updateBook(1L, updatedBook);

        assertEquals(updatedBook.getTitle(), result.getTitle());
        assertEquals(updatedBook.isRented(), result.isRented());
        assertEquals(updatedBook.getPlace(), result.getPlace());
        assertEquals(updatedBook.getName(), result.getName());
        assertEquals(updatedBook.getReturnDate(), result.getReturnDate());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(book1);
    }

    @Test
    public void testDeleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        boolean isDeleted = bookService.deleteBook(1L);

        assertEquals(true, isDeleted);
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}
