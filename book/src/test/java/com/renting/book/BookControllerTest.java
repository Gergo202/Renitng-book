/*package com.renting.book;

import com.renting.book.controller.BookController;
import com.renting.book.entity.BookEntity;
import com.renting.book.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;*/

//@SpringBootTest
//@AutoConfigureMockMvc
/*public class BookControllerTest {
    @Mock
    private BookService bookService;
    @InjectMocks
    private BookController bookController;
    @Autowired
    private MockMvc mockMvc;
    private SimpleDateFormat dateFormat =
            new SimpleDateFormat("yyyy-MM-dd");
    private BookEntity book1;
    private BookEntity book2;
    @BeforeEach
    public void setup() throws Exception {
        MockitoAnnotations.openMocks(this);
        book1 = new BookEntity(1L, "Book1", false,
                "A100", null, null);
        book2 = new BookEntity(2L, "Book2", true,
                "A101", "User", dateFormat.parse("2024-06-01"));
    }
    @Test
    public void testGetAllBooks() throws Exception {
        List<BookEntity> books = Arrays.asList(book1, book2);
        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Book1")))
                .andExpect(jsonPath("$[0].place", is("A100")))
                .andExpect(jsonPath("$[0].isRented", is(false)))
                .andExpect(jsonPath("$[0].name", is(nullValue())))
                .andExpect(jsonPath("$[0].returnDate", is(nullValue())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Book2")))
                .andExpect(jsonPath("$[1].place", is("A101")))
                .andExpect(jsonPath("$[1].isRented", is(true)))
                .andExpect(jsonPath("$[1].name", is("User")))
                .andExpect(jsonPath("$[1].returnDate", is("2024-06-01")));

        verify(bookService, times(1)).getAllBooks();
    }
    @Test
    public void testGetBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(book1);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Book1")))
                .andExpect(jsonPath("$.place", is("A100")))
                .andExpect(jsonPath("$.isRented", is(false)))
                .andExpect(jsonPath("$.name", is(nullValue())))
                .andExpect(jsonPath("$.returnDate", is(nullValue())));

        verify(bookService, times(1)).getBookById(1L);
    }
    @Test
    public void testGetRentedBooks() throws Exception {
        List<BookEntity> rentedBooks = Arrays.asList(book2);
        when(bookService.getRentedBooks()).thenReturn(rentedBooks);

        mockMvc.perform(get("/books/rented"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].title", is("Book2")))
                .andExpect(jsonPath("$[0].place", is("A101")))
                .andExpect(jsonPath("$[0].isRented", is(true)))
                .andExpect(jsonPath("$[0].name", is("User")))
                .andExpect(jsonPath("$[0].returnDate", is("2024-06-01")));

        verify(bookService, times(1)).getRentedBooks();
    }
    @Test
    public void testSaveBook() throws Exception {
        BookEntity savedBook = new BookEntity(3L, "Book3", false,
                "C100", null, null);
        when(bookService.saveBook(any(BookEntity.class))).thenReturn(savedBook);

        mockMvc.perform(post("/books/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Book3\", \"place\": \"C100\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("Book3")))
                .andExpect(jsonPath("$.place", is("C100")))
                .andExpect(jsonPath("$.isRented", is(false)))
                .andExpect(jsonPath("$.name", is(nullValue())))
                .andExpect(jsonPath("$.returnDate", is(nullValue())));


        verify(bookService, times(1)).saveBook(any(BookEntity.class));
    }
    @Test
    public void testUpdateBook() throws Exception {
        BookEntity updatedBook = new BookEntity(1L, "Book4",
                true, "B100",
                "User2", dateFormat.parse("2024-06-01"));
        when(bookService.updateBook(eq(1L), any(BookEntity.class))).thenReturn(updatedBook);

        mockMvc.perform(put("/books/edit/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Book4\", \"isRented\": true," +
                                " \"place\": \"B100\", \"name\": \"User2\", " +
                                "\"returnDate\": \"2024-06-01\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Book4")))
                .andExpect(jsonPath("$.isRented", is(true)))
                .andExpect(jsonPath("$.place", is("B100")))
                .andExpect(jsonPath("$.name", is("User2")))
                .andExpect(jsonPath("$.returnDate", is("2024-06-01")));

        verify(bookService, times(1)).updateBook(eq(1L), any(BookEntity.class));
    }
    @Test
    public void testDeleteBook() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/books/delete/1"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBook(1L);
    }
}*/
