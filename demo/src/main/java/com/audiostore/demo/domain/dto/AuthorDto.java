package com.audiostore.demo.domain.dto;

import com.audiostore.demo.domain.models.Author;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDto {
    
    private long id;
    private String name;
    private byte[] picture;

    public static AuthorDto convert(Author author){
        return new AuthorDto(author.getId(), author.getName(), author.getPicture());
    }

}
