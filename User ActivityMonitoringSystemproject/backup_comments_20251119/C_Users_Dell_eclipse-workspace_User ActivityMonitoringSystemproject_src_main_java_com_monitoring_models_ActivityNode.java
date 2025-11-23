package com.monitoring.models;



public class ActivityNode {
    public ActivityLog.ActivityRecord data;   
    public ActivityNode prev;                 
    public ActivityNode next;                

    public ActivityNode(ActivityLog.ActivityRecord data) {
        this.data = data;
    }
}

