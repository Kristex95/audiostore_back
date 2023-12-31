package com.audiostore.demo.api.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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

    private static final String UPLOAD_DIR = "demo/public/";
    
    @GetMapping("images/{filePath}")
    public @ResponseBody void getPicture(HttpServletRequest request, HttpServletResponse response, @PathVariable String filePath){
        response.reset();
        response.setBufferSize(2048 * 20);
        try {
            File file = new File(UPLOAD_DIR + "images" + "/" + filePath);
            //mimetype
            String mimeType = Files.probeContentType(file.toPath());
            response.setContentType(mimeType);

            FileInputStream input = new FileInputStream(file);
            response.getOutputStream().write(input.readAllBytes());
            input.close();
        } catch (IOException e) {
            // Do something
        }
    }    

    @GetMapping("audio/{filePath}")
    public ResponseEntity<byte[]>  getAudio(HttpServletRequest request, HttpServletResponse response, @PathVariable String filePath){
        response.reset();
        response.setBufferSize(2048 * 20);
        try {
            File file = new File(UPLOAD_DIR + "audio" + "/" + filePath);
            //mimetype
            String mimeType = Files.probeContentType(file.toPath());
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(mimeType));
            headers.setContentLength(file.length());
            headers.setContentDispositionFormData("attachment", file.getName());

            byte[] audio = Files.readAllBytes(file.toPath());
            return new ResponseEntity<>(audio, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  
}
