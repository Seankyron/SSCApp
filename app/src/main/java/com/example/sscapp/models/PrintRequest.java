package com.example.sscapp.models;

import android.net.Uri;

public class PrintRequest {
    private Uri fileUri;
    private String paperSize;
    private int numberOfCopies;
    private String dateOfClaiming;
    private String remarks;

    public PrintRequest(Uri fileUri, String paperSize, int numberOfCopies, String dateOfClaiming, String remarks) {
        this.fileUri = fileUri;
        this.paperSize = paperSize;
        this.numberOfCopies = numberOfCopies;
        this.dateOfClaiming = dateOfClaiming;
        this.remarks = remarks;
    }

    // Getters and setters
    public Uri getFileUri() {
        return fileUri;
    }

    public void setFileUri(Uri fileUri) {
        this.fileUri = fileUri;
    }

    public String getPaperSize() {
        return paperSize;
    }

    public void setPaperSize(String paperSize) {
        this.paperSize = paperSize;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public String getDateOfClaiming() {
        return dateOfClaiming;
    }

    public void setDateOfClaiming(String dateOfClaiming) {
        this.dateOfClaiming = dateOfClaiming;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}