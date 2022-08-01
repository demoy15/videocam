package com.example.videocam;

import com.example.videocam.models.*;
import com.example.videocam.repositories.IVideoCameraRepository;
import com.example.videocam.repositories.VideoCameraRepository;
import com.example.videocam.services.CameraService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CameraServiceTest {

    private final IVideoCameraRepository cameraRepository = Mockito.mock(VideoCameraRepository.class);

    @Test
    void getAllCameras() {
        var id = 11;
        var value = "fa4b5b22-249b-11e9-ab14-d663bd873d93";
        var ttl = "33";
        var urlType = UrlType.ARCHIVE;
        var videoUrl = "http://result.ru";
        List<InfoData> infoDataList = new ArrayList<>();
        var infoData = new InfoData();
        infoData.setId(id);
        infoData.setSourceDataUrl("http://sourceData.ru");
        infoData.setTokenDataUrl("http://tokenData.ru");
        infoDataList.add(infoData);

        var sourceData = new SourceData();
        sourceData.setVideoUrl(videoUrl);
        sourceData.setUrlType(urlType);

        var tokenData = new TokenData();
        tokenData.setValue(value);
        tokenData.setTtl(ttl);

        List<Camera> cameras = new ArrayList<>();
        var camera = new Camera();
        camera.setId(id);
        camera.setValue(value);
        camera.setUrlType(urlType);
        camera.setVideoUrl(videoUrl);
        camera.setTtl(ttl);
        cameras.add(camera);

        Mockito.when(cameraRepository.getAllCameraInfos()).thenReturn(CompletableFuture.completedFuture(infoDataList));
        Mockito.when(cameraRepository.getSourceData(infoData.getSourceDataUrl())).thenReturn(CompletableFuture.completedFuture(sourceData));
        Mockito.when(cameraRepository.getTokenData(infoData.getTokenDataUrl())).thenReturn(CompletableFuture.completedFuture(tokenData));
        CameraService cameraService = new CameraService(cameraRepository);
        var result = cameraService.getAllCameras().join();

        assertEquals(cameras, result);
    }
}
