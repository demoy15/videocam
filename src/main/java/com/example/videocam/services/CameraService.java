package com.example.videocam.services;

import com.example.videocam.models.Camera;
import com.example.videocam.models.InfoData;
import com.example.videocam.repositories.IVideoCameraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class CameraService implements ICameraService {

    private final IVideoCameraRepository videoCameraRepository;

    @Autowired
    public CameraService(IVideoCameraRepository videoCameraRepository) {
        this.videoCameraRepository = videoCameraRepository;
    }

    public CompletableFuture<List<Camera>> getAllCameras() {


        List<InfoData> infoData = videoCameraRepository.getAllCameraInfos().join();

        List<CompletableFuture<Camera>> futureList = infoData.stream()
                .map(data -> {
                    Camera camera = new Camera();
                    camera.setId(data.getId());

                    return fillData(data.getSourceDataUrl(), data.getTokenDataUrl(), camera);
                }).toList();
        var allFutures = CompletableFuture.allOf(
                futureList.toArray(new CompletableFuture[0])
        );

        return allFutures.thenApply(v -> futureList.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList()));
    }

    private CompletableFuture<Camera> fillData(String sourceDataUrl, String tokenDataUrl, Camera camera) {
        return videoCameraRepository.getSourceData(sourceDataUrl).thenComposeAsync(sourceData -> {
            camera.setVideoUrl(sourceData.getVideoUrl());
            camera.setUrlType(sourceData.getUrlType());
            return videoCameraRepository.getTokenData(tokenDataUrl).thenComposeAsync(tokenData -> {
                camera.setValue(tokenData.getValue());
                camera.setTtl(tokenData.getTtl());
                return CompletableFuture.completedFuture(camera);
            });
        });
    }
}
