package com.audiostore.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.audiostore.demo.domain.dto.SongDto;
import com.audiostore.demo.domain.models.Author;
import com.audiostore.demo.domain.models.Song;
import com.audiostore.demo.repository.SongRepository;
import com.audiostore.demo.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SongService {

    private final String AUDIO_PATH = "audio/";
    private final String PICTURE_PATH = "picture/";

    private static final String IMAGE_UPLOAD_DIR = "demo/public/images/";
    private static final String AUDIO_UPLOAD_DIR = "demo/public/audio/";

    private final SongRepository songRepository;
    private final AuthorService authorService;

    public Song getSong(long id){
        Song song = songRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid song id " + id));
        return song;
    }
    public List<SongDto> getAll(){
        return songRepository.findAll().stream().map(song->SongDto.convert(song)).toList();
    }

    public Song createSong(String name, long author_id, MultipartFile picture, MultipartFile audio) throws IOException{

        Author author = authorService.getAuthor(author_id);

        FileUploadUtil.saveFile(picture, IMAGE_UPLOAD_DIR);
        FileUploadUtil.saveFile(audio, AUDIO_UPLOAD_DIR);

        Song song = Song.builder()
              .name(name)
              .author(author)
              .picture_path(PICTURE_PATH + picture.getOriginalFilename())
              .audio_path(AUDIO_PATH + audio.getOriginalFilename())
              .build();
        return songRepository.save(song);
    }

    public Song updateSong(long id, String name, long author_id, MultipartFile picture, MultipartFile audio){

        Author author = authorService.getAuthor(author_id);
        Song song = getSong(id);

        FileUploadUtil.saveFile(picture, IMAGE_UPLOAD_DIR);
        FileUploadUtil.saveFile(audio, AUDIO_UPLOAD_DIR);

        song.setName(name);
        song.setPicture_path(PICTURE_PATH + picture.getOriginalFilename());
        song.setAudio_path(AUDIO_PATH + audio.getOriginalFilename());
        song.setAuthor(author);
        
        return songRepository.save(song);
    }

    public void deleteSong(long id){
        Song song = getSong(id);
        songRepository.delete(song);
    }
}
