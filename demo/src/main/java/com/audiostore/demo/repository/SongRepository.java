package com.audiostore.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.audiostore.demo.domain.models.Song;

public interface SongRepository extends JpaRepository<Song, Long>{
    
}
