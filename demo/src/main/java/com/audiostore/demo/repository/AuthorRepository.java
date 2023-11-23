package com.audiostore.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.audiostore.demo.domain.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    
}
