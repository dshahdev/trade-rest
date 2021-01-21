package com.shahs.transactions.controller;

import com.shahs.transactions.TransactionsApplication;
import com.shahs.transactions.service.storage.DocumentStorageService;
import com.shahs.transactions.service.storage.HelloMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins ={"http://127.0.0.1:4200",
        "http://192.168.1.103:4200",
        "http://192.168.1.103:8080",
        "http://169.254.10.157:4200",
        "http://localhost:4200"})

@RequestMapping("/api/v1")
public class FileController {
    @Autowired
    private DocumentStorageService documneStorageService;

    @Autowired
    private HelloMessageService helloMessageService;

    @PostMapping("/uploadCSVFile")
    public Map<String, Boolean>  uploadFile(@Valid @RequestBody MultipartFile file) {

        String fileName = file.getOriginalFilename();

        documneStorageService.storeFile(file,fileName);


//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();

        Map<String, Boolean> response = new HashMap<>();
        response.put("result", Boolean.TRUE);

        return response;
    }

    @PostMapping("/processCSVFile")
    public Map<String, Boolean>  processFile(@Valid @RequestBody String file) {

        Map<String, Boolean> response = new HashMap<>();
        response.put("result", Boolean.FALSE);

        try {
            helloMessageService.printCsvFile(TransactionsApplication.UPLOAD_DIR, file);
            response.put("result", Boolean.TRUE);
        } catch (IOException ex) {
            System.out.println("IO Exception while trying to load data from csv file");
        }

        return response;
    }

    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String fileName,
                                                 HttpServletRequest request) {

        Resource resource = null;
        if(fileName !=null && !fileName.isEmpty()) {
            try {
                resource = documneStorageService.loadFileAsResource(fileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Try to determine file's content type
            String contentType = null;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                //logger.info("Could not determine file type.");
            }
            // Fallback to the default content type if type could not be determined
            if(contentType == null) {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }

    }
}
