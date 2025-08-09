package com.pm.bookapiproject.repositories;

import com.pm.bookapiproject.TestDataUtil;
import com.pm.bookapiproject.domain.Author;
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
public class AuthorRepositoryIntegrationTest {

    private final AuthorRepository underTest;

    @Autowired
    AuthorRepositoryIntegrationTest(AuthorRepository underTest ) {
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = underTest.save(author);

        Optional<Author> result = underTest.findById(savedAuthor.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedAuthor);
    }
    @Test
    public void testThatManyAuthorsCanBeCreatedAndRecalled(){
        underTest.deleteAll();
        Author authorA = TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        authorA.setId(null);authorB.setId(null);authorC.setId(null);

        Author savedAuthorA = underTest.save(authorA);
        Author savedAuthorB = underTest.save(authorB);
        Author savedAuthorC = underTest.save(authorC);

        Iterable<Author> result = underTest.findAll();

        assertThat(result).hasSize(3)
                .containsExactly(savedAuthorA , savedAuthorB, savedAuthorC);
    }

    @Test
    public void testThatAuthorCanBeUpdate(){
        underTest.deleteAll();
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = underTest.save(author);

        savedAuthor.setName("UPDATED");

        underTest.save( savedAuthor);

        Optional<Author> result = underTest.findById(savedAuthor.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(savedAuthor);

    }
    @Test
    public void testThatAuthorCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = underTest.save(author);

        underTest.deleteById(savedAuthor.getId());
        Optional<Author> result = underTest.findById(savedAuthor.getId());
        assertThat(result).isEmpty();

    }

    @Test
    public void testThatGetAuthorsWithAgeLessThan(){
        underTest.deleteAll();
        Author authorA= TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        authorA.setId(null) ; authorB.setId(null) ; authorC.setId(null);
        Author savedAuthorA = underTest.save(authorA);
        Author savedAuthorB= underTest.save(authorB);
        Author saveAuthorC= underTest.save(authorC);

        Iterable<Author> result =  underTest.ageLessThan(50);

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(authorB , authorC);
    }
    @Test
    public void testThatGetAuthorsWithAgeGreaterThan(){
        underTest.deleteAll();
        Author authorA= TestDataUtil.createTestAuthor();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        authorA.setId(null) ; authorB.setId(null) ; authorC.setId(null);
        Author savedAuthorA = underTest.save(authorA);
        Author savedAuthorB= underTest.save(authorB);
        Author saveAuthorC= underTest.save(authorC);



        Iterable<Author> result = underTest.findAuthoWithAgeGreaterThan(50);
        assertThat(result).hasSize(1)
                .containsExactly(savedAuthorA);
    }

}
