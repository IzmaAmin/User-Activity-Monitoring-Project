package com.monitoring.utils;

import com.monitoring.models.Document;
import com.monitoring.models.User;

import java.util.Random;



public class DataGenerator {
    
    
    private static final String[] purposes = {"personal", "work", "company"};
    
    
    private static final Random rnd = new Random();

    
    public static User[] generateDummyUsers(int count) {
        User[] users = new User[count];
        
        for (int i = 0; i < count; i++) {
            
            String username = "user_" + (i + 1);
            String fullName = "User " + (i + 1);
            String password = "pass" + (i + 1);
            String email = username + "@example.com";
            String phone = String.format("+1-555-%04d", i);  
            
            
            String purpose = purposes[rnd.nextInt(purposes.length)];
            
            
            String ip = randomIP();
            
            
            User user = new User(username, password, fullName, email, phone, purpose, ip);
            
            
            if (i % 7 == 0) {
                user.addDocument(new Document("resume.docx", "doc"));
            }
            if (i % 11 == 0) {
                user.addDocument(new Document("bank_statement.pdf", "pdf"));
            }
            
            users[i] = user;
        }
        
        return users;
    }

    
    public static String randomIP() {
        return String.format("%d.%d.%d.%d", 
            rnd.nextInt(256),  
            rnd.nextInt(256),  
            rnd.nextInt(256),  
            rnd.nextInt(256)  
        );
    }
}

