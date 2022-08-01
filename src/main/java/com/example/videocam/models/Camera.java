package com.example.videocam.models;

import lombok.Data;

@Data
public class Camera {
    private int id;
    private UrlType urlType;
    private String videoUrl;
    private String value;
    private String ttl;
}
