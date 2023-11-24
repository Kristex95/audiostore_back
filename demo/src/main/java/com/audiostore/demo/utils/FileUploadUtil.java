package com.audiostore.demo.utils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
    
    public static void saveFile(MultipartFile file, String directory){
        if (file.isEmpty()) {
            System.out.println("no file");
        }
        try{
            String fileName = file.getOriginalFilename();
            Path filePath = Path.of(directory + fileName);

            // File existingFile = new File(directory + fileName);
            // if(!existingFile.exists()){
            //     System.out.println("file not exists. Creating new file...");
            // }
            

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(Exception e){
            System.out.println(e);
        }    
    }
}
