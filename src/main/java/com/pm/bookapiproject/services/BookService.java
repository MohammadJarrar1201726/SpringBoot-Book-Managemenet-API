package com.pm.bookapiproject.services;

import com.pm.bookapiproject.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    Book saveBook(String isbn , Book bookDto);
    List<Book> getAllBooks();
    Page<Book> getAllBooks(Pageable pageable);
    Optional<Book> getBookById(String isbn);

    boolean isExists(String isbn);

    Book partialUpdateBook(String isbn, Book book);

    void delete(String isbn);


}
