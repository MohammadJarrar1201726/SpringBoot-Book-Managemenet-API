package com.pm.bookapiproject.mappers.impl;

import com.pm.bookapiproject.domain.Author;
import com.pm.bookapiproject.dto.AuthorDto;
import com.pm.bookapiproject.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements Mapper<Author, AuthorDto> {

    private final ModelMapper modelMapper;
    @Autowired
    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public AuthorDto mapTo(Author author) {
        return modelMapper.map(author , AuthorDto.class);
    }

    @Override
    public Author mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto , Author.class);
    }
}
