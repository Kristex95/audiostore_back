package com.audiostore.demo.api.controllers;

import org.springframework.http.HttpStatus;

import java.security.Principal;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.audiostore.demo.domain.dto.LoginDto;
import com.audiostore.demo.domain.dto.UserDto;
import com.audiostore.demo.service.SecurityService;
import com.audiostore.demo.service.UserPermissionService;
import com.audiostore.demo.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/user/")
@RequiredArgsConstructor
public class UserControler {
    private final UserService userService;
    private final SecurityService securityService;
    private final UserPermissionService permissionService;

    @PostMapping("login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDto dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>("Huy", HttpStatus.BAD_REQUEST);
        }
        try {
            securityService.login(dto.getLogin(), dto.getPassword());
            UserDto user = UserDto.convert(userService.getUserByEmail(dto.getLogin()));
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Bad Creds", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("new")
    public ResponseEntity<?> newUser(@RequestBody UserDto user) {
        System.out.println("new User");
        userService.createUser(user);
        System.out.println("registered new user: " + user.getFirstName() + " " + user.getLastName());
        securityService.login(user.getEmail(), user.getPassword());
        System.out.println("logged in new user: " + user.getFirstName() + " " + user.getLastName());
        // securityService.login(user.getEmail(), user.getPassword());
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("{userId}")
    public ResponseEntity<?> getUserDetails(@PathVariable long userId, Authentication authentication) {
            // Extracting the username from Basic Authentication
            String username =  authentication.getName();

            if (permissionService.isAuthenticated(userId, username) || permissionService.isAdmin(username)) {
                return new ResponseEntity<>(UserDto.convert(userService.getUserById(userId)), new HttpHeaders(),
                        HttpStatus.OK);
            }
        
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("all")
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(UserDto.convert(userService.getAll()), new HttpHeaders(), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("{userId}")
    public ResponseEntity<?> updateUser(@PathVariable long userId,
            @RequestBody UserDto userDto,
            Principal auth) {
        if (permissionService.isAuthenticated(userId, auth.getName())) {
            return new ResponseEntity<>(UserDto.convert(userService.updateUser(userId, userDto)), new HttpHeaders(),
                    HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PutMapping("song/{songId}")
    public ResponseEntity<?> saveSong(@PathVariable("songId") long song_id, Principal auth) {
        long user_id = userService.getUserByEmail(auth.getName()).getId();
        return new ResponseEntity<>(UserDto.convert(userService.addSong(user_id, song_id)), new HttpHeaders(),
                HttpStatus.OK);
    }
}
