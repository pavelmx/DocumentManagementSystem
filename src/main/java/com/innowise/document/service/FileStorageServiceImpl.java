package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.file.FileStorage;
import com.innowise.document.file.ResponseFile;
import com.innowise.document.repository.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService{

    private final Path fileStorageLocation;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    public FileStorageServiceImpl(FileStorage fileStorage) {
        this.fileStorageLocation = Paths.get(fileStorage.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public ResponseFile addFile(MultipartFile file, Long id_document) {
        String uuidFile = UUID.randomUUID().toString();
        String atachDate =  LocalDate.now().getDayOfMonth() + "-"
                + LocalDate.now().getMonthValue() + "-" + LocalDate.now().getYear();
        String fileName = StringUtils.cleanPath(atachDate + "." + uuidFile + "." + file.getOriginalFilename());
        Document doc = documentRepo.getOne(id_document);
        doc.setFilename(fileName);
        documentRepo.save(doc);
        try {
            if (fileName.contains("..")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            ResponseFile responseFile = new ResponseFile(fileName, file.getOriginalFilename(), file.getContentType(), file.getSize());
            return responseFile;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file '" + fileName + "'. Please try again!", ex);
        }
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File " + fileName + " can't be create.");
            }
        } catch (MalformedURLException ex) {
            throw new  RuntimeException("File not found " + fileName, ex);
        }
    }

    /*@Override
    public void deleteAll(){
        FileSystemUtils.deleteRecursively(fileStorageLocation.toFile());
    }*/
}
