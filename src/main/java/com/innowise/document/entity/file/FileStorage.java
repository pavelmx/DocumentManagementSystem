package com.innowise.document.entity.file;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class FileStorage {

    @Value("${file.upload-dir}")
    private String uploadDir;

}
