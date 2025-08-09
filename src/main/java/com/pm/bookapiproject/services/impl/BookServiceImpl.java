package com.pm.bookapiproject.services.impl;

import com.pm.bookapiproject.domain.Book;
import com.pm.bookapiproject.repositories.AuthorRepository;
import com.pm.bookapiproject.repositories.BookRepository;
import com.pm.bookapiproject.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }
    @Override
    public Book saveBook(String isbn , Book book) {
        book.setIsbn(isbn);
        return  bookRepository.save(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> getBookById(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public Book partialUpdateBook(String isbn, Book book) {
        book.setIsbn(isbn);

        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(book.getTitle()).ifPresent(existingBook::setTitle);
            return bookRepository.save(existingBook);

        }).orElseThrow( () -> new RuntimeException("Book not found"));


    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }
}
