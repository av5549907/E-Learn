package com.elearn.app.controllers;

import com.elearn.app.dtos.CustomMessage;
import com.elearn.app.dtos.ResourceContentType;
import com.elearn.app.dtos.VideoDto;
import com.elearn.app.entities.Video;
import com.elearn.app.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/v1/videos")
public class VideoController {

    @Autowired
    VideoService videoService;

    @PostMapping
    ResponseEntity<VideoDto> createVideo(@RequestBody VideoDto videoDto) {
        return new ResponseEntity<>(videoService.createVideo(videoDto), HttpStatus.CREATED);
    }

    @GetMapping("/{videoId}")
    ResponseEntity<VideoDto> getVideo(@PathVariable String videoId) {
        return new ResponseEntity<>(videoService.getVideo(videoId), HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<Page<VideoDto>> getAllVideos(Pageable pageable) {
        return new ResponseEntity<>(videoService.getAllVideos(pageable), HttpStatus.OK);
    }

    @PutMapping("/{videoId}")
    ResponseEntity<VideoDto> updateVideo(@RequestBody VideoDto videoDto, @PathVariable String videoId) {
        return new ResponseEntity<>(videoService.updateVideo(videoDto, videoId), HttpStatus.OK);
    }

    @DeleteMapping("/videoId")
    ResponseEntity<String> deleteVideo(@PathVariable String videoId) {
        return new ResponseEntity<>(videoService.deleteVideo(videoId), HttpStatus.OK);
    }

    @GetMapping("/search")
    ResponseEntity<List<VideoDto>> searchVideo(@RequestParam String keyWord) {
        return new ResponseEntity<>(videoService.searchVideo(keyWord), HttpStatus.OK);
    }

    @PostMapping("/{videoId}/videos")
    ResponseEntity<?> uploadVideos(
            @PathVariable String videoId,
            @RequestParam("video") MultipartFile video
    ) throws IOException {
        String contentType = video.getContentType();
        if (contentType != null || (!contentType.equalsIgnoreCase("video/mp4") || !contentType.equalsIgnoreCase("video/mov") || !contentType.equalsIgnoreCase("video/AVI") || !contentType.equalsIgnoreCase("video/mkv"))) {
            CustomMessage customMessage = new CustomMessage();
            customMessage.setMessage("Invalid file !!");
            customMessage.setSuccess(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);
        }
        System.out.println(video.getContentType());
        System.out.println(video.getOriginalFilename());
        System.out.println(video.getName());
        System.out.println(video.getSize());

        return ResponseEntity.ok(videoService.saveVideo(video, videoId));
    }

    @GetMapping("/{videoId}/videos")
    ResponseEntity<Resource> serveBanner(
            @PathVariable String videoId
    ) {
        ResourceContentType resourceContentType = videoService.getVideoById(videoId);
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(resourceContentType.getContentType())).body(resourceContentType.getResource());
    }
}
