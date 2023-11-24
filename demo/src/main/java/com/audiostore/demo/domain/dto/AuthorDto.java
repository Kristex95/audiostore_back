package com.audiostore.demo.domain.dto;

import java.util.List;

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
    private String picture_path;
    private List<SongDto> songs;

    public static AuthorDto convert(Author author) {
        return new AuthorDto(author.getId(), author.getName(), author.getPicture_path(), author.getSongs().stream().map(song->SongDto.convert(song)).toList());
    }

}
