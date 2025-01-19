package com.elearn.app.servicesImpl;

import com.elearn.app.Config.AppConstants;
import com.elearn.app.dtos.CourseDto;
import com.elearn.app.dtos.ResourceContentType;
import com.elearn.app.dtos.VideoDto;
import com.elearn.app.entities.Course;
import com.elearn.app.entities.Video;
import com.elearn.app.exceptions.ResourceNotFoundException;
import com.elearn.app.repositories.CourseRepo;
import com.elearn.app.repositories.VideoRepo;
import com.elearn.app.services.FileService;
import com.elearn.app.services.VideoService;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class VideoServiceImpl implements VideoService {

    VideoRepo videoRepo;
    ModelMapper modelMapper;

    FileService fileService;
    CourseRepo courseRepo;

    public VideoServiceImpl(VideoRepo videoRepo, ModelMapper modelMapper, FileService fileService, CourseRepo courseRepo) {
        this.videoRepo = videoRepo;
        this.modelMapper = modelMapper;
        this.fileService = fileService;
        this.courseRepo = courseRepo;
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

    @Override
    public VideoDto saveVideo(MultipartFile file, String videoId) throws IOException {
        Video video=videoRepo.findById(videoId).orElseThrow(()->new ResourceNotFoundException("Video not found!!"));
        String filePath=fileService.save(file, AppConstants.COURSE_VIDEO_UPLOAD_DIR,file.getOriginalFilename());
        video.setFilePath(filePath);
        video.setContentType(file.getContentType());
        Video savedVideo= videoRepo.save(video);
        return modelMapper.map(savedVideo, VideoDto.class);
    }

    @Override
    public ResourceContentType getVideoById(String videoId) {
        Video video=videoRepo.findById(videoId).orElseThrow(()->new ResourceNotFoundException("Video not found!!"));
        String filePath=video.getFilePath();
        Path path= Paths.get(filePath);
        Resource resource=new FileSystemResource(path);
        ResourceContentType resourceContentType=new ResourceContentType();
        resourceContentType.setResource(resource);
        resourceContentType.setContentType(video.getContentType());
        return resourceContentType;
    }
}
