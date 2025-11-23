package com.monitoring.utils;

import com.monitoring.models.ActivityLog;

import java.util.List;



public class RecursionDetector {
    
    
    public static boolean hasRepeatedFailedAttempts(
            List<ActivityLog.ActivityRecord> records, 
            String username, 
            int threshold) {
        return helper(records, username, threshold, 0, 0);
    }

    
    private static boolean helper(
            List<ActivityLog.ActivityRecord> records, 
            String username, 
            int threshold, 
            int idx, 
            int count) {
        
        if (count >= threshold) return true;
        
        if (idx >= records.size()) return false;
        
        ActivityLog.ActivityRecord record = records.get(idx);
        
        if (record.getUsername().equalsIgnoreCase(username) && 
            record.getAction().toLowerCase().contains("failed")) {
            
            return helper(records, username, threshold, idx + 1, count + 1);
        } else {
            
            return helper(records, username, threshold, idx + 1, count);
        }
    }
}

