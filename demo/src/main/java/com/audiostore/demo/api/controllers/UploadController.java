package com.audiostore.demo.api.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/uploads/")
@RequiredArgsConstructor
public class UploadController {

    private static final String PICTURE_UPLOAD_DIR = "demo/public/images/";
    private static final String AUDIO_UPLOAD_DIR = "demo/public/audio/";
    
    @GetMapping("/picture/{filePath}")
    public @ResponseBody void getPicture(HttpServletRequest request, HttpServletResponse response, @PathVariable String filePath){
        response.reset();
        response.setBufferSize(2048 * 20);
        response.setContentType("image/xyz");
        try {
            File file = new File(PICTURE_UPLOAD_DIR + filePath);
            FileInputStream input = new FileInputStream(file);
            response.getOutputStream().write(input.readAllBytes());
            input.close();
        } catch (IOException e) {
            // Do something
        }
    }

    @GetMapping("/audio/{filePath}")
    public @ResponseBody void getSong(HttpServletRequest request, HttpServletResponse response, @PathVariable String filePath){
        response.reset();
        response.setBufferSize(2048 * 20);
        response.setContentType("audio/mpeg");
        try {
            File file = new File(AUDIO_UPLOAD_DIR + filePath);
            FileInputStream input = new FileInputStream(file);
            response.getOutputStream().write(input.readAllBytes());
            input.close();
        } catch (IOException e) {
            // Do something
        }
    }
    
}
