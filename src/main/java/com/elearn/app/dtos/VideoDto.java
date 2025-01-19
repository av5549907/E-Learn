package com.elearn.app.dtos;

import com.elearn.app.entities.Course;

public class VideoDto {
    private  String videoId;
    private  String videoTitle;
    private  String videoDesc;
    private String filePath;
    private  String contentType;

    private Course course;

    public String getVideoUrl(){
        return "http://localhost:8080/api/v1/videos/"+videoId+"/videos";
    }
}
