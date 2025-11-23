package com.monitoring.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ActivityLog {

   
    public static class ActivityRecord {
        private String username; 
        private String action;                
        private LocalDateTime timestamp;      
        private String ip;                   
        private int severity;                 
        public ActivityRecord(String username, String action, String ip, int severity) {
            this.username = username;
            this.action = action;
            this.ip = ip;
            this.severity = severity;
            this.timestamp = LocalDateTime.now();  // Current date and time
        }

        public String getUsername() { return username; }
        public String getAction() { return action; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public String getIp() { return ip; }
        public int getSeverity() { return severity; }
    }

    
    
    private ActivityNode head;  
    private ActivityNode tail;  
    private int size = 0;       

    public synchronized void add(ActivityRecord r) {
        ActivityNode n = new ActivityNode(r);
        
        if (head == null) {
            head = tail = n;
        } else {
           
            tail.next = n;     
            n.prev = tail;      
            tail = n;           
        }
        size++;  
    }

    
    public List<ActivityRecord> toList() {
        List<ActivityRecord> out = new ArrayList<>();
        ActivityNode cur = head;
        
        while (cur != null) {
            out.add(cur.data);  
            cur = cur.next;      
        }
        return out;
    }

    
    public List<ActivityRecord> findBySeverity(int severity) {
        List<ActivityRecord> out = new ArrayList<>();
        ActivityNode cur = head;
        
        while (cur != null) {
            if (cur.data.getSeverity() == severity) out.add(cur.data);
            cur = cur.next;  
        }
        return out;
    }

    
    public long countByActionPrefix(String prefix) {
        ActivityNode cur = head;
        long c = 0;
        
        while (cur != null) {
            if (cur.data.getAction().startsWith(prefix)) c++;
            cur = cur.next;  
        }
        return c;
    }

    
    public int size() { return size; }
}

