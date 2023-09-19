package com.chunjae.friendy.csv;

import com.chunjae.friendy.csv.entity.CSVFile;
import com.chunjae.friendy.csv.utils.StorageException;
import com.chunjae.friendy.csv.utils.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequestMapping("/admin/csv")
public class CSVController {

    private final CSVService csvService;

    @Autowired
    public CSVController(CSVService csvService) {
        this.csvService = csvService;
    }

    @GetMapping("")
    public String index(Model model) {
        List<CSVFile> csvFileList = csvService.loadAll();
        CSVFile currentFile = csvService.findByCurrentData();
        model.addAttribute("files", csvFileList);
        model.addAttribute("currentFile", currentFile);
        return "admin/pages/csv/index";
    }

    @PostMapping("/upload")
    public String addCSVFile(@RequestParam("csvFile") MultipartFile file, Authentication authentication) throws StorageException {
        String adminId = authentication.getName();
        csvService.store(file, adminId);
        return "redirect:/admin/csv";
    }

    @PostMapping("/{filename:.+}")
    public ResponseEntity<Void> applyCSVFile(@PathVariable String filename) throws IOException {
        try {
            csvService.apply(filename);
            return ResponseEntity.ok().build();
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @DeleteMapping("/{filename:.+}")
    public ResponseEntity<Void> deleteCSVFile(@PathVariable String filename) throws IOException {
        csvService.delete(filename);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) throws IOException {
        Resource resource = csvService.loadAsResource(filename);
        String encodedFileName = new String(filename.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", encodedFileName);
        headers.setContentLength(resource.contentLength());
        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
