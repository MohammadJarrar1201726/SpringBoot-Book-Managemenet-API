package com.pm.bookapiproject.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.bookapiproject.TestDataUtil;
import com.pm.bookapiproject.domain.Author;
import com.pm.bookapiproject.dto.AuthorDto;
import com.pm.bookapiproject.services.AuthorService;
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

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTest(MockMvc mockMvc , AuthorService authorService ) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);

        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );

    }

    @Test
    public void testThatCreatedAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        Author testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);

        String authorJson = objectMapper.writeValueAsString(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Mohammad Jarrar")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );

    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception{


        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception{
        Author testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);
        authorService.save(testAuthor);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").value("Mohammad Jarrar")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").value(80)
        );

    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception{
        Author testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);
        authorService.save(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorReturnsHttpStatus404WhenAuthorDoesNotExist() throws Exception{


        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAuthorReturnsAnAuthorWhenAuthorExist() throws Exception{
        Author testAuthor = TestDataUtil.createTestAuthor();
        testAuthor.setId(null);
        authorService.save(testAuthor);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Mohammad Jarrar")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception{

        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = authorService.save(author);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenAuthorDoesNotExist() throws Exception{
        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);


        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }
    @Test
    public void testThatFullUpdateAuthorWhenExists() throws Exception{
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = authorService.save(author);

       Author authorB = TestDataUtil.createTestAuthorB();
       authorB.setId(savedAuthor.getId());

       String authorJson =  objectMapper.writeValueAsString(authorB);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(authorB.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorB.getAge())
        );

    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHttpStatus200WhenAuthorExist() throws Exception{

        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = authorService.save(author);

        AuthorDto authorDto = TestDataUtil.createTestAuthorDto();
        authorDto.setName("UPDATED");
        String authorDtoJson = objectMapper.writeValueAsString(authorDto);


        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorWhenExists() throws Exception{
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = authorService.save(author);

        Author authorB = TestDataUtil.createTestAuthorB();
        authorB.setName("UPDATED");
        authorB.setId(savedAuthor.getId());

        String authorJson =  objectMapper.writeValueAsString(authorB);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(savedAuthor.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("UPDATED")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(authorB.getAge())
        );

    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForNonExistingAuthor() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/99" )
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteAuthorReturnsHttpStatus204ForExistingAuthor() throws Exception{
        Author author = TestDataUtil.createTestAuthor();
        author.setId(null);
        Author savedAuthor = authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId() )
                        .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }



}
