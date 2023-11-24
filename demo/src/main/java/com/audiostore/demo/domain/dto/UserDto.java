package com.audiostore.demo.domain.dto;

import java.util.List;

import com.audiostore.demo.domain.models.Role;
import com.audiostore.demo.domain.models.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private List<SongDto> songs;

    public static UserDto convert(User user) {
        return new UserDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), user.getRole(), user.getSongs().stream().map(song->SongDto.convert(song)).toList());
    }

    public static List<UserDto> convert(List<User> users) {
        return users.stream().map(user -> convert(user)).toList();
    }
}