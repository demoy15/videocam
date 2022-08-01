package com.example.videocam.repositories;

import com.example.videocam.models.InfoData;
import com.example.videocam.models.SourceData;
import com.example.videocam.models.TokenData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public class VideoCameraRepository implements IVideoCameraRepository {


    private final HttpClient httpClient;

    @Value("${variables.url}")
    private String url;


    @Autowired
    public VideoCameraRepository() {

        this.httpClient = HttpClient.newHttpClient();

    }

    @Override
    public CompletableFuture<SourceData> getSourceData(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(s -> new Handler().getMappedValue(s, SourceData.class));
    }

    @Override
    public CompletableFuture<TokenData> getTokenData(String url) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(s -> new Handler().getMappedValue(s, TokenData.class));
    }

    @Override
    public CompletableFuture<List<InfoData>> getAllCameraInfos() {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        var res = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
                .thenApply(s -> new Handler().getMappedValues(s, InfoData.class)).join();
        return CompletableFuture.completedFuture(res);
    }


}
