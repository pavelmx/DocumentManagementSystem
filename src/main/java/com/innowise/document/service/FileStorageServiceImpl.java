package com.innowise.document.service;

import com.innowise.document.entity.documents.*;
import com.innowise.document.entity.file.FileStorage;
import com.innowise.document.entity.file.ResponseFile;
import com.innowise.document.repository.documents.*;
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

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    WorkContractRepo workContractRepo;

    @Autowired
    CreditContractRepo creditContractRepo;

    @Autowired
    RentalContractRepo rentalContractRepo;

    @Autowired
    CooperationContractRepo cooperationContractRepo;

    @Autowired
    ContractOfSaleRepo contractOfSaleRepo;

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
        Path workStorageLocation = getUserFileStorageLocation().resolve("work").toAbsolutePath().normalize();
        Path saleStorageLocation = getUserFileStorageLocation().resolve("sale").toAbsolutePath().normalize();
        Path creditStorageLocation = getUserFileStorageLocation().resolve("credit").toAbsolutePath().normalize();
        Path cooperationStorageLocation = getUserFileStorageLocation().resolve("cooperation").toAbsolutePath().normalize();
        Path rentalStorageLocation = getUserFileStorageLocation().resolve("rental").toAbsolutePath().normalize();
        try {
            Files.createDirectories(userFileStorageLocation);
            Files.createDirectories(workStorageLocation);
            Files.createDirectories(saleStorageLocation);
            Files.createDirectories(creditStorageLocation);
            Files.createDirectories(cooperationStorageLocation);
            Files.createDirectories(rentalStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    @Override
    public ResponseFile addFile(MultipartFile file, Long id_document, String kind){
        createUserDirectory();
        Path targetLocation = Paths.get("");
        DocumentPattern doc = new DocumentPattern();
        switch (kind){
            case "work":{
                doc = workContractRepo.getOne(id_document);
                break;
            }
            case "cooperation":{
                doc = cooperationContractRepo.getOne(id_document);
                break;
            }
            case "credit":{
                doc = creditContractRepo.getOne(id_document);
                break;
            }
            case "sale":{
                doc = contractOfSaleRepo.getOne(id_document);
                break;
            }
            case "rental":{
                doc = rentalContractRepo.getOne(id_document);
                break;
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd--HH-mm");
        String dateTime = LocalDateTime.now().format(formatter);
        String fileName = StringUtils.cleanPath("(" + dateTime + "--" + doc.getTitle() + ")" + file.getOriginalFilename());
        try {
            if (fileName.contains("...")) {
                throw new RuntimeException("Sorry! Filename contains invalid path sequence " + fileName);
            }
            doc.setFilename(fileName);
            switch (kind){
                case "work":{
                    workContractRepo.save((WorkContract) doc);
                    targetLocation = getUserFileStorageLocation().resolve(kind).resolve(fileName);
                    break;
                }
                case "cooperation":{
                    cooperationContractRepo.save((CooperationContract) doc);
                    targetLocation = getUserFileStorageLocation().resolve(kind).resolve(fileName);
                    break;
                }
                case "credit":{
                    creditContractRepo.save((CreditContract) doc);
                    targetLocation = getUserFileStorageLocation().resolve(kind).resolve(fileName);
                    break;
                }
                case "sale":{
                    contractOfSaleRepo.save((ContractOfSale) doc);
                    targetLocation = getUserFileStorageLocation().resolve(kind).resolve(fileName);
                    break;
                }
                case "rental":{
                    rentalContractRepo.save((RentalContract) doc);
                    targetLocation = getUserFileStorageLocation().resolve(kind).resolve(fileName);
                    break;
                }
            }

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            ResponseFile responseFile = new ResponseFile(fileName, file.getOriginalFilename(), file.getContentType(), file.getSize());
            return responseFile;
        } catch (IOException ex) {
            throw new RuntimeException("Could not store file '" + fileName + "'. Please try again!", ex);
        }


    }

    @Override
    public Resource loadFileAsResource(String fileName, String kind){
        try {
            Path filePath = Paths.get("");
            DocumentPattern doc = new DocumentPattern();
            switch (kind){
                case "work":{
                    doc = workContractRepo.findByFilename(fileName).get();
                    filePath = getUserFileStorageLocation().resolve(kind).resolve(fileName).normalize();
                    break;
                }
                case "cooperation":{
                    doc = cooperationContractRepo.findByFilename(fileName).get();
                    filePath = getUserFileStorageLocation().resolve(kind).resolve(fileName).normalize();
                    break;
                }
                case "credit":{
                    doc = creditContractRepo.findByFilename(fileName).get();
                    filePath = getUserFileStorageLocation().resolve(kind).resolve(fileName).normalize();
                    break;
                }
                case "sale":{
                    doc = contractOfSaleRepo.findByFilename(fileName).get();
                    filePath = getUserFileStorageLocation().resolve(kind).resolve(fileName).normalize();
                    break;
                }
                case "rental":{
                    doc = rentalContractRepo.findByFilename(fileName).get();
                    filePath = getUserFileStorageLocation().resolve(kind).resolve(fileName).normalize();
                    break;
                }
            }
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                doc.setFilename(null);
                switch (kind){
                    case "work":{
                        workContractRepo.save((WorkContract) doc);
                        break;
                    }
                    case "cooperation":{
                        cooperationContractRepo.save((CooperationContract) doc);
                        break;
                    }
                    case "credit":{
                        creditContractRepo.save((CreditContract) doc);
                        break;
                    }
                    case "sale":{
                        contractOfSaleRepo.save((ContractOfSale) doc);
                        break;
                    }
                    case "rental":{
                        rentalContractRepo.save((RentalContract) doc);
                        break;
                    }
                }
                throw new NoSuchFileException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        } catch (NoSuchFileException ex) {
            throw new RuntimeException("File not found " + fileName, ex);
        }
    }

    @Override
    public void deleteFile(String fileName, String kind){
        DocumentPattern doc = new DocumentPattern();
        switch (kind) {
            case "work": {
                doc = workContractRepo.findByFilename(fileName).get();
                break;
            }
            case "cooperation": {
                doc = cooperationContractRepo.findByFilename(fileName).get();
                break;
            }
            case "credit": {
                doc = creditContractRepo.findByFilename(fileName).get();
                break;
            }
            case "sale": {
                doc = contractOfSaleRepo.findByFilename(fileName).get();
                break;
            }
            case "rental": {
                doc = rentalContractRepo.findByFilename(fileName).get();
                break;
            }
        }
        Path path = Paths.get("");

        System.out.println("do  -----------  " + doc.getFilename());

        switch (kind){
            case "work":{
                path = Paths.get(getUserFileStorageLocation() + "\\" + kind +"\\" + doc.getFilename()).toAbsolutePath().normalize();
                doc.setFilename(null);
                workContractRepo.save((WorkContract) doc);
                 break;
            }
            case "cooperation":{
                path = Paths.get(getUserFileStorageLocation() + "\\" + kind +"\\" + doc.getFilename()).toAbsolutePath().normalize();
                doc.setFilename(null);
                cooperationContractRepo.save((CooperationContract) doc);
                break;
            }
            case "credit":{
                path = Paths.get(getUserFileStorageLocation() + "\\" + kind +"\\" + doc.getFilename()).toAbsolutePath().normalize();
                doc.setFilename(null);
                creditContractRepo.save((CreditContract) doc);
                 break;
            }
            case "sale":{
                path = Paths.get(getUserFileStorageLocation() + "\\" + kind +"\\" + doc.getFilename()).toAbsolutePath().normalize();
                doc.setFilename(null);
                contractOfSaleRepo.save((ContractOfSale) doc);
                break;
            }
            case "rental":{
                path = Paths.get(getUserFileStorageLocation() + "\\" + kind +"\\" + doc.getFilename()).toAbsolutePath().normalize();
                doc.setFilename(null);
                rentalContractRepo.save((RentalContract) doc);
                 break;
            }
        }
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
