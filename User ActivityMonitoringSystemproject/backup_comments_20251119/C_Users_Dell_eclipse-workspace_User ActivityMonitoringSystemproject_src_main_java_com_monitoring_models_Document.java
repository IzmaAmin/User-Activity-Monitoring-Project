package com.monitoring.models;



public class Document {
    private String name;    
    private String type;    

    
    public Document(String name, String type) {
        this.name = name;
        this.type = type;
    }

    
    public String getName() { return name; }
    public String getType() { return type; }
}

