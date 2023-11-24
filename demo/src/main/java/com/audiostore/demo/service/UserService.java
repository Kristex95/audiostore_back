package com.audiostore.demo.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.audiostore.demo.domain.dto.UserDto;
import com.audiostore.demo.domain.models.Song;
import com.audiostore.demo.domain.models.User;
import com.audiostore.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SongService songService;



//    public User findByName(String username) throws UsernameNotFoundException {
//        User user = userRepository.findByName(username);
//        if (user == null) {
//            throw new UsernameNotFoundException(username);
//        }
//        return user;
//    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Invalid user id " + id));
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User getUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(email);
        }
        return user.get();
    }

    public User createUser(UserDto userDTO) {
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .role(userDTO.getRole())
                .build();
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        User user = getUserById(id);
        //там стоит cascade all, по идее должен удалять все
        userRepository.delete(user);
    }

    public User updateUser(long id, UserDto userDTO) {
        User user = getUserById(id);
       
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        if (userDTO.getPassword() != null && userDTO.getPassword().length() > 0) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
    
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals("")) {
            user.setEmail(userDTO.getEmail());
        }
    
        return userRepository.save(user);
    }

    public User addSong(long user_id, long song_id){
        User user = getUserById(user_id);
        Song song = songService.getSong(song_id);
        if(song != null){
            user.addSong(songService.getSong(song_id));
        }
        else{
            throw new IllegalStateException("no song with id " + song_id);
        }
        return userRepository.save(user);
    }
}