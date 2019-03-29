package com.innowise.document.controller;

import com.innowise.document.file.ResponseFile;
import com.innowise.document.service.FileStorageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
    private FileStorageServiceImpl fileStorageService;

    @PostMapping("upload/{id_document}")
    public ResponseFile uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("id_document") Long id_document) {
        return fileStorageService.addFile(file, id_document);
    }

    /*@PostMapping("/multiupload")
    public List<ResponseFile> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        List<ResponseFile> r =  Arrays.asList(files)
                    .stream()
                    .map(file -> uploadFile(file))
                    .collect(Collectors.toList());
        return r;
        }*/

    @GetMapping("download/{fileName:.+}")
    public ResponseEntity< Resource > downloadFile(@PathVariable String fileName, HttpServletRequest request) throws IOException{
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

    /*@DeleteMapping("/deleteall")
    public ResponseEntity<Void> deleteAllFiles(){
        fileStorageService.deleteAll();
        return new ResponseEntity<>(HttpStatus.OK);
    }*/
}

