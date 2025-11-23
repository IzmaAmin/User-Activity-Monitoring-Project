package com.monitoring.models;

import java.util.ArrayList;
import java.util.List;



public class User {

    private String username;             
    private String password;              
    private String fullName;             
    private String email;                
    private String phone;                
    private String purpose;              
    private String ipAddress;            
    
    private List<Document> documents = new ArrayList<>();  

    public User(String username, String password, String fullName, String email, 
                String phone, String purpose, String ipAddress) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.purpose = purpose;
        this.ipAddress = ipAddress;
    }

    
    
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public String getPurpose() { return purpose; }
    public String getIpAddress() { return ipAddress; }
    public List<Document> getDocuments() { return documents; }




    public void addDocument(Document d) { 
        documents.add(d); 
    }
}

