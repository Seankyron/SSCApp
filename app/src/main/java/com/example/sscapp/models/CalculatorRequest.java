package com.example.sscapp.models;

public class CalculatorRequest {
    private String studentName;
    private String calculatorNumber;
    private String purpose;
    private String date;
    private boolean isReturned;

    public CalculatorRequest(String studentName, String calculatorNumber, String purpose, String date) {
        this.studentName = studentName;
        this.calculatorNumber = calculatorNumber;
        this.purpose = purpose;
        this.date = date;
        this.isReturned = false;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCalculatorNumber() {
        return calculatorNumber;
    }

    public String getPurpose() {
        return purpose;
    }

    public String getDate() {
        return date;
    }

    public boolean isReturned() {
        return isReturned;
    }

    public void setReturned(boolean returned) {
        isReturned = returned;
    }
}

