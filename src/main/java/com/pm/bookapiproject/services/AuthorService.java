package com.pm.bookapiproject.services;

import com.pm.bookapiproject.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    Author save(Author author);
    List<Author> getAllAuthors();
    Optional<Author> getAuthorById(Long id);
    boolean isExists(Long id);
    Author partialUpdate(Long id, Author author);

    void delete(Long id);
}
