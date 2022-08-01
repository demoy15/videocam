package com.example.videocam.repositories;

import com.example.videocam.models.InfoData;
import com.example.videocam.models.SourceData;
import com.example.videocam.models.TokenData;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface IVideoCameraRepository {

    CompletableFuture<SourceData> getSourceData(String url);

    CompletableFuture<TokenData> getTokenData(String url);

    CompletableFuture<List<InfoData>> getAllCameraInfos();
}
