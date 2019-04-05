package com.innowise.document.service;

import com.innowise.document.entity.Document;
import com.innowise.document.entity.User;
import com.innowise.document.file.FileStorage;
import com.innowise.document.file.ResponseFile;
import com.innowise.document.repository.DocumentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    DocumentRepo documentRepo;

    @Autowired
    FileStorage fileStorage;

    @Autowired
    public FileStorageServiceImpl(FileStorage fileStorage){
        this.fileStorageLocation = Paths.get(fileStorage.getUploadDir())
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }


    private void createUserDirectory(){
        Path userFileStorageLocation = getUserFileStorageLocation().toAbsolutePath().normalize();
        try {
            Files.createDirectories(userFileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public ResponseFile addFile(MultipartFile file, Long id_document){
        createUserDirectory();
        Document doc = documentRepo.getOne(id_document);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm");
        String dateTime = LocalDateTime.now().format(formatter);
        String fileName = StringUtils.cleanPath("(" + dateTime + "--" + doc.getTitle() + ")" + file.getOriginalFilename());
        try {
            if (fileName.contains("...")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            doc.setFilename(fileName);
            documentRepo.save(doc);
            Path targetLocation = getUserFileStorageLocation().resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            ResponseFile responseFile = new ResponseFile(fileName, file.getOriginalFilename(), file.getContentType(), file.getSize());
            return responseFile;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file '" + fileName + "'. Please try again!", ex);
        }


    }

    @Override
    public Resource loadFileAsResource(String fileName){
        try {
            Document doc = documentRepo.findByFilename(fileName).get();
            Path filePath = getUserFileStorageLocation().resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                doc.setFilename(null);
                documentRepo.save(doc);
                System.out.println("title is " + doc.getTitle());
                throw new NoSuchFileException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        } catch (NoSuchFileException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

    @Override
    public void deleteFile(Document document){
        Path path = Paths.get(getUserFileStorageLocation() + "\\" + document.getFilename()).toAbsolutePath().normalize();
        document.setFilename(null);
        documentRepo.save(document);
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getUserFileStorageLocation(){
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        Path userFileStorageLocation = Paths.get(fileStorage.getUploadDir() + "\\" + user)
                .toAbsolutePath().normalize();
        return userFileStorageLocation;
    }
}
