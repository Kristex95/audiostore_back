package com.audiostore.demo.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.audiostore.demo.domain.dto.SongDto;
import com.audiostore.demo.domain.models.Author;
import com.audiostore.demo.domain.models.Song;
import com.audiostore.demo.repository.AuthorRepository;
import com.audiostore.demo.repository.SongRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongService {

    private final String AUDIO_PATH = "audio/";
    private final String PICTURE_PATH = "picture/";

    private final SongRepository songRepository;
    private final AuthorRepository authorRepository;

    public SongDto getSong(long id){
        Song song = songRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid song id " + id));
        return SongDto.convert(song);
    }

    public SongDto createSong(String name, long author_id, MultipartFile picture, MultipartFile audio) throws IOException{
        Author author = authorRepository.findById(author_id).orElseThrow(() ->
                new IllegalArgumentException("Invalid author id " + author_id));
        Song song = Song.builder()
              .name(name)
              .author(author)
              .picture_path(PICTURE_PATH + picture.getOriginalFilename())
              .audio_path(AUDIO_PATH + audio.getOriginalFilename())
              .build();
        songRepository.save(song);
        return SongDto.convert(song);
    }
}
