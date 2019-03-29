package com.innowise.document.file;

public class ResponseFile {
    private String fileName;
    private String fileOriginalName;
    private String fileType;
    private long size;

    public ResponseFile(String fileName, String fileOriginalName, String fileType, long size){
        this.fileName = fileName;
        this.fileOriginalName = fileOriginalName;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName(){
        return fileName;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }

    public String getFileOriginalName(){
        return fileOriginalName;
    }

    public void setFileOriginalName(String fileOriginalName){
        this.fileOriginalName = fileOriginalName;
    }

    public String getFileType(){
        return fileType;
    }

    public void setFileType(String fileType){
        this.fileType = fileType;
    }

    public long getSize(){
        return size;
    }

    public void setSize(long size){
        this.size = size;
    }
}
