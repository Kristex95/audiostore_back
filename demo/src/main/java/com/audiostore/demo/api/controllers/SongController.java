package com.audiostore.demo.api.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.audiostore.demo.service.SongService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/song/")
@RequiredArgsConstructor
public class SongController {

    private final SongService songService;
    private static final String IMAGE_UPLOAD_DIR = "demo/public/images/";
    private static final String AUDIO_UPLOAD_DIR = "demo/public/audio/";


    @GetMapping("{songId}")
    public ResponseEntity<?> getSong(HttpServletRequest request, HttpServletResponse response, @PathVariable long songId) throws IOException{
        return new ResponseEntity<>(songService.getSong(songId), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("new")
    public ResponseEntity<?> newSong(   @RequestParam("name") String name, 
                                        @RequestParam("author_id") long author_id,
                                        @RequestParam("picture") MultipartFile picture,
                                        @RequestParam("audio") MultipartFile audio) 
                                        throws IOException{

        //image
        if (picture.isEmpty()) {
            System.out.println("no file");
        }
        try{
            String fileName = picture.getOriginalFilename();
            Path filePath = Path.of(IMAGE_UPLOAD_DIR + fileName);
            Files.copy(picture.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e){
            System.out.println(e);
        }    
        

        //audio
        if (audio.isEmpty()) {
            System.out.println("no file");
        }
        try{
            String fileName = audio.getOriginalFilename();
            Path filePath = Path.of(AUDIO_UPLOAD_DIR + fileName);
            Files.copy(audio.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e){
            System.out.println(e);
        }
        
        songService.createSong(name, author_id, picture, audio);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
