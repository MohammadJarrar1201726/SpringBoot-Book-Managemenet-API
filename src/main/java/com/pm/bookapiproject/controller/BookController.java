package com.pm.bookapiproject.controller;

import com.pm.bookapiproject.domain.Book;
import com.pm.bookapiproject.dto.BookDto;
import com.pm.bookapiproject.mappers.impl.BookMapper;
import com.pm.bookapiproject.repositories.BookRepository;
import com.pm.bookapiproject.services.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;

    @Autowired
    public BookController(BookService bookService , BookMapper bookMapper, BookRepository bookRepository) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable(name = "isbn") String isbn , @RequestBody BookDto bookDto){
        Book book = bookMapper.mapFrom(bookDto);
        boolean bookExists = bookService.isExists(isbn);
        Book savedBook = bookService.saveBook( isbn ,book);
        BookDto savedUpdatedBookDto = bookMapper.mapTo(savedBook);

        if(bookExists){
            return new ResponseEntity<>(savedUpdatedBookDto, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(savedUpdatedBookDto , HttpStatus.CREATED);

        }


    }
    @GetMapping(path = "/books")
    public Page<BookDto> getAllBooks(Pageable pageable){
        Page<Book> books = bookService.getAllBooks(pageable);
        return books.map(bookMapper::mapTo);
    }

    @GetMapping(path = "/books/{isbn}" )
    public ResponseEntity<BookDto> getBookById(@PathVariable("isbn") String isbn){
        Optional<Book> book = bookService.getBookById(isbn);

        return book.map(b -> {
            BookDto bookDto = bookMapper.mapTo(b);
            return new ResponseEntity<>(bookDto , HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable("isbn") String isbn , @RequestBody BookDto bookDto){


        Book book = bookMapper.mapFrom(bookDto);
        if(! bookService.isExists(isbn)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Book updatedBook = bookService.partialUpdateBook(isbn, book);
        return new ResponseEntity<>(bookMapper.mapTo(updatedBook) , HttpStatus.OK);
    }


    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(@PathVariable("isbn") String isbn ){
        bookService.delete(isbn);
        return new ResponseEntity(HttpStatus.NO_CONTENT);

    }

}
