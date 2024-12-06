package com.example.sscapp.models;

import java.util.Date;

public class Payment {
    private String studentName;
    private String referenceNumber;
    private double amount;
    private Date date;
    private String status;

    public Payment(String studentName, String referenceNumber, double amount, Date date, String status) {
        this.studentName = studentName;
        this.referenceNumber = referenceNumber;
        this.amount = amount;
        this.date = date;
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

