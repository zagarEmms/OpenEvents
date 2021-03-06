package com.example.openevents.business;

import java.io.Serializable;

public class Assistance implements Serializable {

    private int user_id;
    private int event_id;
    private int puntuation;
    private String comentary;

    public Assistance(int user_id, int event_id, int puntuation, String comentary) {
        this.user_id = user_id;
        this.event_id = event_id;
        this.puntuation = puntuation;
        this.comentary = comentary;
    }

    public int getEvent_id() {
        return event_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getPuntuation() {
        return puntuation;
    }

    public String getComentary() {
        return comentary;
    }
}
