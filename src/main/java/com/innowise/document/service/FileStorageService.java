package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.file.ResponseFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

public interface FileStorageService{

    ResponseFile addFile(MultipartFile file, Long id_document);

    Resource loadFileAsResource(String filename);

    void deleteFile(Document document);

}

