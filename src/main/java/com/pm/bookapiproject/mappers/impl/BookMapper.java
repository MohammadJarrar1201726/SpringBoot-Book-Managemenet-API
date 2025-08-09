package com.pm.bookapiproject.mappers.impl;

import com.pm.bookapiproject.domain.Book;
import com.pm.bookapiproject.dto.BookDto;
import com.pm.bookapiproject.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<Book , BookDto> {

    private final ModelMapper modelMapper;
    @Autowired
    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public BookDto mapTo(Book book) {
        return modelMapper.map(book, BookDto.class);
    }

    @Override
    public Book mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, Book.class);
    }
}
