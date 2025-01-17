package com.elearn.app.servicesImpl;

import com.elearn.app.dtos.VideoDto;
import com.elearn.app.entities.Video;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.VideoRepo;
import com.elearn.app.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class VideoServiceImpl implements VideoService {

    VideoRepo videoRepo;
    ModelMapper modelMapper;
    VideoServiceImpl(VideoRepo videoRepo,ModelMapper modelMapper){
        this.videoRepo=videoRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public VideoDto createVideo(VideoDto videoDto) {
        Video video=modelMapper.map(videoDto,Video.class);
        videoRepo.save(video);
        return videoDto;
    }

    @Override
    public VideoDto getVideo(String videoId) {
        Video video=videoRepo.findById(videoId).orElseThrow(()->new  ResourceNotFoundException("Video not found"));
        return modelMapper.map(video,VideoDto.class);
    }

    @Override
    public Page<VideoDto> getAllVideos(Pageable pageable) {
        Page<Video> pageVideo=videoRepo.findAll(pageable);
        List<VideoDto> videoDtoList=pageVideo.getContent().stream().map(video -> modelMapper.map(video,VideoDto.class)).collect(Collectors.toList());
        return new PageImpl<>(videoDtoList,pageable,pageVideo.getTotalElements());
    }

    @Override
    public VideoDto updateVideo(VideoDto videoDto, String videoId) {
        Video video=videoRepo.findById(videoId).orElseThrow(()->new ResourceNotFoundException("Video not found !!"));
        modelMapper.map(videoDto,video);
        videoRepo.save(video);
        return modelMapper.map(video,VideoDto.class);
    }

    @Override
    public String deleteVideo(String videoId) {
        Video video=videoRepo.findById(videoId).orElseThrow(()->new ResourceNotFoundException("Video not found !!"));
        videoRepo.delete(video);
        return "Video with videoId "+videoId+" deleted SuccessFully";
    }

    @Override
    public List<VideoDto> searchVideo(String keyword) {
        List<Video> videos=videoRepo.findByVideoTitleContainingIgnoreCaseOrVideoDescContainingIgnoreCase(keyword,keyword);
            return videos.stream().map(video -> modelMapper.map(video,VideoDto.class)).collect(Collectors.toList());
    }
}
