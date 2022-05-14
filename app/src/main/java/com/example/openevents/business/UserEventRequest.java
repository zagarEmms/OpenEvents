package com.example.openevents.business;

public class UserEventRequest {
    private int fieldCount;
    private int affectedRows;
    private int insertId;
    private String info;
    private int serverStatus;
    private int warningStatus;

    public UserEventRequest(int fieldCount, int affectedRows, int insertId, String info, int serverStatus, int warningStatus) {
        this.fieldCount = fieldCount;
        this.affectedRows = affectedRows;
        this.insertId = insertId;
        this.info = info;
        this.serverStatus = serverStatus;
        this.warningStatus = warningStatus;
    }
}
