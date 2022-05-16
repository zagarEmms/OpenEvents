package com.example.openevents.business;

public class Assistance {

    private int user_id;
    private int event_id;
    private int puntuation;
    private String comentary;

    public Assistance(int puntuation, String comentary) {
        this.puntuation = puntuation;
        this.comentary = comentary;
    }
}
