package com.innowise.document.service;

import com.innowise.document.entity.file.ResponseFile;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService{

    ResponseFile addFile(MultipartFile file, Long id_document, String name);

    Resource loadFileAsResource(String filename, String name);

    void deleteFile(String fileName, String name);

}

