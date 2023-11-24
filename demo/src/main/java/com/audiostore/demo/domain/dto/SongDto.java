package com.audiostore.demo.domain.dto;


import com.audiostore.demo.domain.models.Song;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SongDto {
    private long id;
    private String name;
    private String picture_path;
    private String audio_path;

    public static SongDto convert(Song song){
        return new SongDto(song.getId(), song.getName(), song.getPicture_path(), song.getAudio_path());
    }
}
