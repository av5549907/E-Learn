package com.elearn.app.services;

import com.elearn.app.dtos.VideoDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VideoService {

     VideoDto createVideo(VideoDto videoDto);

     VideoDto getVideo(String videoId);
     Page<VideoDto> getAllVideos(Pageable pageable);
     VideoDto updateVideo(VideoDto videoDto,String videoId);
     String deleteVideo(String videoId);
     List<VideoDto> searchVideo(String keyWord);

}
