package com.audiostore.demo.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.audiostore.demo.domain.dto.AuthorDto;
import com.audiostore.demo.domain.models.Author;
import com.audiostore.demo.repository.AuthorRepository;
import com.audiostore.demo.utils.ImageUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {
    
    private final AuthorRepository authorRepository;
    
    public List<Author> getAll(){
        return authorRepository.findAll();
    }
    
    public AuthorDto getAuthor(long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow();
        return AuthorDto.builder().name(author.getName()).picture(ImageUtil.decompressImage(author.getPicture())).build();
    }

    public Author createAuthor(String name, MultipartFile picture) throws IOException{
        Author author = Author.builder()
            .name(name)
            .picture(ImageUtil.compressImage(picture.getBytes()))
            .build();
        return authorRepository.save(author);
    }

    
}
