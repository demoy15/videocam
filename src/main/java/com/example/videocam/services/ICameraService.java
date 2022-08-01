package com.example.videocam.services;

import com.example.videocam.models.Camera;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICameraService {
    CompletableFuture<List<Camera>> getAllCameras();
}
