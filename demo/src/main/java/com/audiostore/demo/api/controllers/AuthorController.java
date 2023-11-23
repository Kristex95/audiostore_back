package com.audiostore.demo.api.controllers;

import java.io.IOException;

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

import com.audiostore.demo.service.AuthorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/author/")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("{author_id}")
    public ResponseEntity<?> getAuthor(@PathVariable long author_id) {
        return new ResponseEntity<>(authorService.getAuthor(author_id), new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping("new")
    public ResponseEntity<?> newAuthor(@RequestParam("picture") MultipartFile picture, @RequestParam("name") String name) throws IOException{
        authorService.createAuthor(name, picture);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
