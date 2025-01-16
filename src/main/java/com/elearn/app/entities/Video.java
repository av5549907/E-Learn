package com.elearn.app.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Entity
@Table(name="videos")
@Data
public class Video {
    @Id
    private  String videoId;
    private  String videoTitle;
    private  String videoDesc;
    private String filePath;
    private  String contentType;
    @ManyToOne(optional=false)
    @JoinColumn(name="courseId", nullable=false, updatable=false)
    private  Course course;
}
