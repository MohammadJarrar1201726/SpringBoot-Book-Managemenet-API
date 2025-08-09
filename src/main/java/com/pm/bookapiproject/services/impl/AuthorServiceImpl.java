package com.pm.bookapiproject.services.impl;

import com.pm.bookapiproject.domain.Author;
import com.pm.bookapiproject.repositories.AuthorRepository;
import com.pm.bookapiproject.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AuthorServiceImpl  implements AuthorService {

    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    @Override
    public List<Author> getAllAuthors() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Author> getAuthorById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public Author partialUpdate(Long id, Author author) {
        author.setId(id);

        return authorRepository.findById(id).map(auth ->{
            Optional.ofNullable(author.getAge()).ifPresent(auth::setAge);
            Optional.ofNullable(author.getName()).ifPresent(auth::setName);
            return authorRepository.save(auth);
        }).orElseThrow(() -> new RuntimeException("Author Does not Exist"));
    }

    @Override
    public void delete(Long id) {
        authorRepository.deleteById(id);
    }
}
