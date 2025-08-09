
package com.pm.bookapiproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.bookapiproject.TestDataUtil;
import com.pm.bookapiproject.domain.Book;
import com.pm.bookapiproject.dto.BookDto;
import com.pm.bookapiproject.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookControllerIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookService bookService;
    @Autowired
    public BookControllerIntegrationTest(MockMvc mockMvc , BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();

    }

    @Test
    public void testThatBookCanBeCreatedAndReturns201Http() throws Exception {
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);




        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void testThatCreatedBookSuccessfullyReturnsSavedBook() throws Exception{
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        String bookJson = objectMapper.writeValueAsString(bookDto);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + bookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(bookDto.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(bookDto.getTitle())
        );
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception{

        Book book = TestDataUtil.createTestBook(null);
        bookService.saveBook(book.getIsbn() , book);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books" )
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value("1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("FirstBook")
        );


    }

    @Test
    public void testThatGetBooksReturnsHttpStatus200WhenExists() throws Exception{
        Book book = TestDataUtil.createTestBook(null);
        bookService.saveBook(book.getIsbn() , book);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBooksReturnsHttpStatus404WhenDoesNotExists() throws Exception{

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetBookReturnsBookWhenExists() throws Exception{

        Book book = TestDataUtil.createTestBook(null);
        bookService.saveBook(book.getIsbn() , book);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/1" )
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("FirstBook")
        );


    }

    @Test
    public void testThatUpdateBookReturnsHttpStatus200Ok() throws Exception{
        Book book = TestDataUtil.createTestBook(null);
        Book savedBook =  bookService.saveBook(book.getIsbn() , book);
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setIsbn(savedBook.getIsbn());
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }
    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception{
        Book book = TestDataUtil.createTestBook(null);
        Book savedBook =  bookService.saveBook(book.getIsbn() , book);
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setIsbn(savedBook.getIsbn());
        bookDto.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }


    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200Ok() throws Exception{
        Book book = TestDataUtil.createTestBook(null);
        Book savedBook =  bookService.saveBook(book.getIsbn() , book);

        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)

        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

    }

    @Test
    public void testThatPartialUpdateBookReturnsUpdatedBook() throws Exception{
        Book book = TestDataUtil.createTestBook(null);
        Book savedBook =  bookService.saveBook(book.getIsbn() , book);
        BookDto bookDto = TestDataUtil.createTestBookDto(null);
        bookDto.setTitle("UPDATED");
        String bookJson = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)

        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value("1")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("UPDATED")
        );
    }

    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForNonExistingBook() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/99" )
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
    @Test
    public void testThatDeleteBookReturnsHttpStatus204ForExistingBook() throws Exception{
        Book book = TestDataUtil.createTestBook(null);
        Book savedBook = bookService.saveBook(book.getIsbn() , book);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + savedBook.getIsbn() )
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
