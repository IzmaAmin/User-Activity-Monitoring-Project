package com.monitoring.utils;

import com.monitoring.models.ActivityLog;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class SortingAlgorithms {
    
    
    public static void sortByTimestamp(List<ActivityLog.ActivityRecord> list) {
        
        
        Collections.sort(
            list,                                               
            Comparator.comparing(ActivityLog.ActivityRecord::getTimestamp)  
        );
    }
}

