package com.example.videocam.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class Handler extends com.fasterxml.jackson.databind.ObjectMapper {

    public <T> T getMappedValue(String content, Class<T> responseType) {

        T value = null;
        try {
            value = readValue(content, responseType);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public <T> List<T> getMappedValues(String content, Class<T> responseType) {

        JsonNode jsonNode = nullNode();
        try {
            jsonNode = readTree(content);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return convertValue(jsonNode, getTypeFactory().constructCollectionType(List.class, responseType));
    }

}

