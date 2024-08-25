package com.asr.springboot_mongodb.controller;

import com.asr.springboot_mongodb.collection.Photo;
import com.asr.springboot_mongodb.exceptions.ApiLimitExceedException;
import com.asr.springboot_mongodb.ratelimiter.RateLimiter;
import com.asr.springboot_mongodb.service.PhotoService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    private PhotoService photoService;
    @Autowired
    private RateLimiter rateLimiter;
    @SneakyThrows
    @PostMapping
    public String addPhoto(@RequestParam("image")MultipartFile image){
        if( !rateLimiter.tryGet() ) throw new ApiLimitExceedException("Too many requests");
        String id = photoService.addPhoto(image.getOriginalFilename(),image);
        return id;
    }
    @SneakyThrows
    @GetMapping("/{id}")
    public ResponseEntity<Resource> downloadPhoto(@PathVariable String id){
        if( !rateLimiter.tryGet() ) throw new ApiLimitExceedException("Too many requests");
        Photo photo = photoService.getPhoto(id);
        Resource resource = new ByteArrayResource(photo.getPhoto().getData());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename\"=" + photo.getTitle() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
