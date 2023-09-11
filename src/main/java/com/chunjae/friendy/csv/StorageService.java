package com.chunjae.friendy.csv;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.stream.Stream;
import java.nio.file.Path;

public class StorageService {
    void store(MultipartFile file) {

    }

    Stream<Path> loadAll() {
        return null;
    }

    Path load(String fileName) {
        return null;
    }

    Resource loadAsResource(String fileName) {
        return null;
    }

}
