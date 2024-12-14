package com.docsharing.documentshare;

import java.util.Date;

public class DownloadHistory {

    private String fileName;
    private String fileSize;
    private Date downloadDate;

    public DownloadHistory(String fileName, String fileSize, Date downloadDate) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.downloadDate = downloadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public Date getDownloadDate() {
        return downloadDate;
    }
}
