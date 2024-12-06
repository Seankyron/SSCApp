package com.example.sscapp.models;

public class User {
    private String name;
    private String email;
    private String srCode;
    private String department;
    private String program;
    private String year;

    public User(String name, String email, String srCode, String department, String program, String year) {
        this.name = name;
        this.email = email;
        this.srCode = srCode;
        this.department = department;
        this.program = program;
        this.year = year;
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getSrCode() { return srCode; }
    public String getDepartment() { return department; }
    public String getProgram() { return program; }
    public String getYear() { return year; }
}

