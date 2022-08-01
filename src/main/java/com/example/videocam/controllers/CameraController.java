package com.example.videocam.controllers;

import com.example.videocam.models.Camera;
import com.example.videocam.services.ICameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
public class CameraController {

    private final ICameraService cameraService;

    @Autowired
    public CameraController(ICameraService cameraService) {
        this.cameraService = cameraService;
    }


    @GetMapping("/cameras")
    public CompletableFuture<List<Camera>> getAllCameras() {
        return cameraService.getAllCameras();
    }
}
