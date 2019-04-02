package com.innowise.document.controller;

import com.innowise.document.entity.Document;
import com.innowise.document.file.ResponseFile;
import com.innowise.document.service.DocumentService;
import com.innowise.document.service.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    FileStorageServiceImpl fileStorageService;

    @Autowired
    DocumentService documentService;

    @PostMapping("upload/{id_document}")
    public ResponseEntity<ResponseFile> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id_document") Long id_document) {
        return new ResponseEntity<>(fileStorageService.addFile(file, id_document), HttpStatus.OK);
    }

    @GetMapping("download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException{
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @DeleteMapping("delete/{fileName:.+}")
    public ResponseEntity<Void> deleteFileByName(@PathVariable String fileName){
        Document doc = documentService.findByFilename(fileName);
        fileStorageService.deleteFile(doc);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

