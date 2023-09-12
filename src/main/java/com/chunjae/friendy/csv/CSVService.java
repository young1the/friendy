package com.chunjae.friendy.csv;

import com.chunjae.friendy.csv.utils.StorageException;
import com.chunjae.friendy.csv.utils.StorageFileNotFoundException;
import com.chunjae.friendy.csv.utils.StoragePath;
import com.chunjae.friendy.school.School;
import com.chunjae.friendy.school.SchoolAddress;
import com.chunjae.friendy.school.SchoolAddressRepository;
import com.chunjae.friendy.school.SchoolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.nio.file.Path;

@Transactional
@Service
public class CSVService {

    private final CSVFileRepository csvFileRepository;
    private final SchoolRepository schoolRepository;
    private final SchoolAddressRepository schoolAddressRepository;
    private final Path storagePath = StoragePath.INSTANCE.getStorageLocation();

    @Autowired
    public CSVService(CSVFileRepository csvFileRepository, SchoolRepository schoolRepository, SchoolAddressRepository schoolAddressRepository) {
        this.csvFileRepository = csvFileRepository;
        this.schoolRepository = schoolRepository;
        this.schoolAddressRepository = schoolAddressRepository;
    }


    private void storeToLocal(MultipartFile file) {
        try {
            Path destinationFile = this.storagePath.resolve(Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.storagePath.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    private void storeToDataBase(MultipartFile file) {
        csvFileRepository.save(
                CSVFile.builder()
                        .fileName(file.getOriginalFilename())
                        .currentData('N')
                        .build()
        );
    }

    void store(MultipartFile file) throws StorageException {
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }
        storeToLocal(file);
        storeToDataBase(file);
    }

    List<CSVFile> loadAll() {
        return csvFileRepository.findAllByOrderByCreatedAtDesc();
    }

    public Path load(String filename) {
        return storagePath.resolve(filename);
    }

    Resource loadAsResource(String fileName) {
        try {
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + fileName, e);
        }
    }

    public void delete(String filename) throws IOException {
        Path file = load(filename);
        Files.delete(file);
        csvFileRepository.deleteByFileName(filename);
    }

    public void apply(String filename) {
        CSVFile csvFile = csvFileRepository.findByFileName(filename);
        if (csvFile == null) {
            throw new StorageFileNotFoundException("no file found");
        }
        Path file = load(filename);
        if (!Files.exists(file)) {
            throw new StorageFileNotFoundException("no file found");
        }
        try (BufferedReader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String firstLine = reader.readLine();
            CSVParser csvParser = new CSVParser(firstLine);
            String line = "";
            while ((line = reader.readLine()) != null) {
                Pair<School, SchoolAddress> pair = csvParser.getSchoolPair(line);
                School school = pair.getFirst();
                SchoolAddress schoolAddress = pair.getSecond();
                school.setDeletedYn("N");
                school = schoolRepository.save(school);
                schoolAddress.setSchoolIdx(school);
                schoolAddressRepository.save(pair.getSecond());
            }
            csvFile.setCurrentData('Y');
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
