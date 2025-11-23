package com.monitoring.utils;

import com.monitoring.models.ActivityLog;

import java.util.ArrayList;
import java.util.List;






public class SearchingAlgorithms {
    
   
    public static List<ActivityLog.ActivityRecord> searchByUsernameOrIp(
            List<ActivityLog.ActivityRecord> list, 
            String q) {
        
        q = q.toLowerCase();  
        List<ActivityLog.ActivityRecord> results = new ArrayList<>();
        
        
        for (ActivityLog.ActivityRecord record : list) {
            
            if (record.getUsername().toLowerCase().contains(q) || 
                
                record.getIp().toLowerCase().contains(q) || 
                record.getAction().toLowerCase().contains(q)) {
                
                results.add(record);  
            }
        }
        
        return results;
    }
}

