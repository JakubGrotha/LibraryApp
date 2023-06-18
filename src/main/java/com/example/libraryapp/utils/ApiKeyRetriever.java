package com.example.libraryapp.utils;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class ApiKeyRetriever {
    public String getGoogleBooksApiKey() {
        Properties properties = new Properties();
        String homeDirectory = System.getProperty("user.home");
        String configFilePath = homeDirectory.concat("/config.properties");
        try (FileInputStream config = new FileInputStream(configFilePath)) {
            properties.load(config);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty("api.key");
    }
}
