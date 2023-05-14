package com.example.libraryapp.service;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataService <T> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void saveListToFile (Path pathToFile, List<T> listToSave) {
        try {
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            String jsonList = objectMapper.writeValueAsString(listToSave);
            Files.write(pathToFile, jsonList.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<T> readListFromFile (Path pathToFile) {
        try {
            String jsonList = Files.readString(pathToFile);
            return objectMapper.readValue(jsonList, new TypeReference<List<T>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
