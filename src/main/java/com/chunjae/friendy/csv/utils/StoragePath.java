package com.chunjae.friendy.csv.utils;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public enum StoragePath {
    INSTANCE;

    private final Path storageLocation;

    StoragePath() {
        Path rootLocation = Paths.get(System.getProperty("user.dir"));
        storageLocation = rootLocation.resolve("storage");
        try {
            Files.createDirectories(storageLocation);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Path getStorageLocation() {
        return storageLocation;
    }
}