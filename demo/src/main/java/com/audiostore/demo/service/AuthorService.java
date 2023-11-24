package com.audiostore.demo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.audiostore.demo.domain.models.Author;
import com.audiostore.demo.repository.AuthorRepository;
import com.audiostore.demo.utils.FileUploadUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
    
    private final String IMAGES_PATH = "images/";
    private static final String UPLOAD_DIR = "demo/public/images/";
    private final AuthorRepository authorRepository;
    
    public List<Author> getAll(){
        return authorRepository.findAll();
    }
    
    public Author getAuthor(long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        return author;
    }

    public Author createAuthor(String name, MultipartFile picture) throws IOException{
        
        FileUploadUtil.saveFile(picture, UPLOAD_DIR);

        Author author = Author.builder().name(name).picture_path(IMAGES_PATH + picture.getOriginalFilename()).build();
        return authorRepository.save(author);
    }

    public void delete(long authorId) throws IOException {
        Author author = authorRepository.findById(authorId).orElseThrow();
        authorRepository.delete(author);
    }
}
