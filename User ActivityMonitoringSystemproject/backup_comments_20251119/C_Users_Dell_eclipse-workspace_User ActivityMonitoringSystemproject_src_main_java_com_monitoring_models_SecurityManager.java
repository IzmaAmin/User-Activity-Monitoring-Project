package com.monitoring.models;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


public class SecurityManager {

    private Map<String, Integer> attempts = new HashMap<>();
    
    
    private Map<String, LocalDateTime> lockedUntil = new HashMap<>();


    public int recordFailedAttempt(String username) {
        int v = attempts.getOrDefault(username, 0) + 1;
        attempts.put(username, v);  
        return v;
    }

    
    public void resetAttempts(String username) { 
        attempts.remove(username);  
    }


    public void lockAccount(String username, int days) {
        LocalDateTime lockTime = LocalDateTime.now().plusDays(days);
        lockedUntil.put(username, lockTime);
    }

    public boolean isLocked(String username) {
        LocalDateTime lockTime = lockedUntil.get(username);
        
        if (lockTime == null) return false;
        
        if (lockTime.isBefore(LocalDateTime.now())) {
            lockedUntil.remove(username);  
            return false;  
        }
        
        return true;
    }

  
    public int getSuspiciousCount() {
        int count = 0;
        
       
        for (int attemptCount : attempts.values()) {
          
            if (attemptCount >= 4) count++;
        }
        
        return count;
    }
}

