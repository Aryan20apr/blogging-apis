package com.aryan.blogging.bloggingapis.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aryan.blogging.bloggingapis.payload.ApiResponse;
import com.aryan.blogging.bloggingapis.services.MyBlobService;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/blob")
@Slf4j
public class BlobController {
    @Autowired
    private  MyBlobService myBlobService;

    @GetMapping("/")
    public List<String> blobitemst() {
        return myBlobService.listFiles();
    }


    @GetMapping("/download/{filename}")
    public ResponseEntity<?> download(@PathVariable String filename) {
        log.info("download blobitem: {}", filename);
       byte[]file= myBlobService.downloadFile(filename).toByteArray();
       return ResponseEntity.status(HttpStatus.OK)
               .contentType(MediaType.valueOf("application/pdf"))
               .body(file);

    }
    
    @GetMapping("/downloadimage/{filename}")
    public ResponseEntity<?>downloadImage(@PathVariable String filename)
    {
    	
            log.info("download blobitem: {}", filename);
             byte []image= myBlobService.downloadFile(filename).toByteArray();
             return ResponseEntity.status(HttpStatus.OK)
             .contentType(MediaType.valueOf("image/png"))
             .body(image);

    }
    
    @PostMapping("/post/upload")
    public ResponseEntity<ApiResponse<String>> uploadFile(MultipartFile file,@RequestParam Integer id ) throws IOException {
        log.info("Filename :" + file.getOriginalFilename());
        log.info("Size:" + file.getSize());
        log.info("Contenttype:" + file.getContentType());
        log.info("Post Id:" + id);
        
        String url= myBlobService.storeFile(id,file.getOriginalFilename(),file.getInputStream(), file.getSize());
        ApiResponse<String> apiResponse=new ApiResponse<>(url,"mage updated successfully",true);
        return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.CREATED);

    }
    @PostMapping("profile/upload")
    public ResponseEntity<ApiResponse<String>> uploadProfilePicture(MultipartFile file,@RequestParam Integer id ) throws IOException {
        log.info("Filename :" + file.getOriginalFilename());
        log.info("Size:" + file.getSize());
        log.info("Contenttype:" + file.getContentType());
        log.info("Post Id:" + id);
        
        String url= myBlobService.changeProfilePicture(id,file.getOriginalFilename(),file.getInputStream(), file.getSize());
        ApiResponse<String> apiResponse=new ApiResponse<>(url,"Profile picture changed successfully",true);
        return new ResponseEntity<ApiResponse<String>>(apiResponse,HttpStatus.CREATED);

    }
   
}

