package com.audiostore.demo.api.controllers;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.audiostore.demo.domain.dto.AuthorDto;
import com.audiostore.demo.service.AuthorService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/author/")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("{author_id}")
    public ResponseEntity<?> getAuthor(@PathVariable long author_id) {
        return new ResponseEntity<>(AuthorDto.convert(authorService.getAuthor(author_id)), new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAuthor() {
        return new ResponseEntity<>(authorService.getAll(), new HttpHeaders(), HttpStatus.OK);
    }
    

    @PostMapping("new")
    public ResponseEntity<?> newAuthor( @RequestParam("picture") MultipartFile picture, 
                                        @RequestParam("name") String name) 
                                        throws IOException{

        
        return new ResponseEntity<>(authorService.createAuthor(name, picture), new HttpHeaders(), HttpStatus.OK);
    }

    @PutMapping("{author_id}")
    public ResponseEntity<?> newAuthor( @PathVariable long author_id,
                                        @RequestParam("picture") MultipartFile picture, 
                                        @RequestParam("name") String name) 
                                        throws IOException{

        
        return new ResponseEntity<>(AuthorDto.convert(authorService.updateAuthor(author_id, name, picture)), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("{author_id}")
    private ResponseEntity<?> deleteAuthor(@PathVariable long author_id) throws IOException{
        authorService.delete(author_id);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
