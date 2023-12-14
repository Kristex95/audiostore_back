package com.audiostore.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.audiostore.demo.domain.models.Song;

public interface SongRepository extends JpaRepository<Song, Long> {
  @Query("SELECT s, COUNT(u) as userCount FROM User u JOIN u.songs s GROUP BY s ORDER BY userCount DESC")
  List<Object[]> findMostPopularSongs();
}
