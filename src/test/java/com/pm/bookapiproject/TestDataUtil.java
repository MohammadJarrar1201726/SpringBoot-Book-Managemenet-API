package com.pm.bookapiproject;

import com.pm.bookapiproject.domain.Author;
import com.pm.bookapiproject.domain.Book;
import com.pm.bookapiproject.dto.AuthorDto;
import com.pm.bookapiproject.dto.BookDto;


public final class TestDataUtil {
    private TestDataUtil() {

    }
    public static Author createTestAuthor(){
        return  Author.builder()
                .id(1L)
                .name("Mohammad Jarrar")
                .age(80)
                .build();
    }
    public static Author createTestAuthorB(){
        return  Author.builder()
                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }
    public static Author createTestAuthorC(){
        return  Author.builder()
                .id(3L)
                .name("Jess A Casey")
                .age(24)
                .build();
    }

    public static AuthorDto createTestAuthorDto(){
        return  AuthorDto.builder()
                .id(1L)
                .name("Mohammad Jarrar")
                .age(80)
                .build();
    }


    public static AuthorDto createTestAuthorDtoB(){
        return  AuthorDto.builder()
                .id(2L)
                .name("Thomas Cronin")
                .age(44)
                .build();
    }
    public static AuthorDto createTestAuthorDtoC(){
        return  AuthorDto.builder()
                .id(3L)
                .name("Jess A Casey")
                .age(24)
                .build();
    }
    public static Book createTestBook(final Author author){
        return  Book.builder()
                .isbn("1")
                .title("FirstBook")
                .author(author)
                .build();
    }
    public static Book createTestBookB(final Author author){
        return  Book.builder()
                .isbn("2")
                .title("SecondBook")
                .author(author)
                .build();
    }
    public static Book createTestBookC(final Author author){
        return  Book.builder()
                .isbn("3")
                .title("ThirdBook")
                .author(author)
                .build();
    }



    public static BookDto createTestBookDto(final AuthorDto author){
        return  BookDto.builder()
                .isbn("1")
                .title("FirstBook")
                .author(author)
                .build();
    }
    public static BookDto createTestBookDtoB(final AuthorDto author){
        return  BookDto.builder()
                .isbn("2")
                .title("SecondBook")
                .author(author)
                .build();
    }
    public static BookDto createTestBookDtoC(final AuthorDto author){
        return  BookDto.builder()
                .isbn("3")
                .title("ThirdBook")
                .author(author)
                .build();
    }

}
