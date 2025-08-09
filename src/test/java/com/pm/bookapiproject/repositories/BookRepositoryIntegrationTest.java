
package com.pm.bookapiproject.repositories;

import com.pm.bookapiproject.TestDataUtil;
import com.pm.bookapiproject.domain.Author;
import com.pm.bookapiproject.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)

public class BookRepositoryIntegrationTest {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    BookRepositoryIntegrationTest(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;

    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled(){
        //create author first
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = authorRepository.save(author);

        //create a book
        Book book = TestDataUtil.createTestBook(savedAuthor);

        Book savedBook = bookRepository.save(book);

        Optional<Book> result = bookRepository.findById(savedBook.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedBook);

    }

    @Test
    public void testThatManyBooksCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = authorRepository.save(author);

        Book bookA= TestDataUtil.createTestBook(savedAuthor);
        Book bookB= TestDataUtil.createTestBookB(savedAuthor);
        Book bookC= TestDataUtil.createTestBookC(savedAuthor);

        bookRepository.save(bookA);
        bookRepository.save(bookB);
        bookRepository.save(bookC);

        Iterable<Book> result = bookRepository.findAll();

        assertThat(result).hasSize(3);
        assertThat(result).containsExactly(bookA, bookB, bookC);

    }

    @Test
    public void testThatBookCanBeUpdated(){
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = authorRepository.save(author);

        Book book= TestDataUtil.createTestBook(savedAuthor);

        book.setTitle("UPDATED");
        bookRepository.save(book);

        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);

    }

    @Test
    void testThatBookCanBeDeleted(){
        Author author= TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = authorRepository.save(author);
        Book book = TestDataUtil.createTestBook(savedAuthor);

        bookRepository.deleteById(book.getIsbn());

        Optional<Book> result = bookRepository.findById(book.getIsbn());
        assertThat(result).isEmpty();

    }


}
