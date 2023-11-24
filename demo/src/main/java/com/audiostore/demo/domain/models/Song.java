package com.audiostore.demo.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Song",  uniqueConstraints={ @UniqueConstraint(columnNames={"name", "author_id"}),})
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "picture_path")
    private String picture_path;

    @Column(name = "audio_path", nullable = false)
    private String audio_path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
}
