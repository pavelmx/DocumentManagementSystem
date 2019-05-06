package com.innowise.document.entity.file;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseFile {

    private String fileName;
    private String fileOriginalName;
    private String fileType;
    private long size;


}
