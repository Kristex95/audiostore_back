package com.audiostore.demo.api.controllers;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.audiostore.demo.domain.dto.SongDto;
import com.audiostore.demo.service.SongService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/song/")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;



    @GetMapping("{songId}")
    public ResponseEntity<?> getSong(HttpServletRequest request, HttpServletResponse response, @PathVariable long songId) throws IOException{
        return new ResponseEntity<>(SongDto.convert(songService.getSong(songId)), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAuthor() {
        return new ResponseEntity<>(songService.getAll(), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("new")
    public ResponseEntity<?> newSong(   @RequestParam("name") String name, 
                                        @RequestParam("author_id") long author_id,
                                        @RequestParam("picture") MultipartFile picture,
                                        @RequestParam("audio") MultipartFile audio) 
                                        throws IOException{
        
        return new ResponseEntity<>(SongDto.convert(songService.createSong(name, author_id, picture, audio)), new HttpHeaders(), HttpStatus.OK);
    }

    @PatchMapping("{songId}")
    public ResponseEntity<?> updateSong(HttpServletRequest request, HttpServletResponse response, 
                                        @PathVariable long songId,
                                        @RequestParam("name") String name, 
                                        @RequestParam("author_id") long author_id,
                                        @RequestParam("picture") MultipartFile picture,
                                        @RequestParam("audio") MultipartFile audio
                                        ) throws IOException{
        return new ResponseEntity<>(SongDto.convert(songService.updateSong(songId, name, author_id, picture, audio)), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("{songId}")
    public ResponseEntity<?> getSong(@PathVariable long songId){
        songService.deleteSong(songId);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }    

    @GetMapping("popular")
    public ResponseEntity<?> getPopular(HttpServletRequest request, HttpServletResponse response) throws IOException{
        return new ResponseEntity<>(SongDto.convert(songService.getMostPopularSongs()), new HttpHeaders(), HttpStatus.OK);
    }
}
