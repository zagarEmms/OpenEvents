package com.example.openevents.business;

import java.io.Serializable;

public class Event implements Serializable {

    private String name;
    private String image;
    private String location;
    private String description;
    private String eventStart_date;
    private String eventEnd_date;
    private int n_participators;
    private String type;
    private String date;
    private int id;

    public Event(String name, String image, String location, String description, String eventStart_date, String eventEnd_date, int n_participators, String type) {
        this.name = name;
        this.image = image;
        this.location = location;
        this.description = description;
        this.eventStart_date = eventStart_date;
        this.eventEnd_date = eventEnd_date;
        this.n_participators = n_participators;
        this.type = type;
    }

    public Event(String name, String image, String location, String description, String eventStart_date, String eventEnd_date, int n_participators, String type, String date, int id) {
        this.name = name;
        this.image = image;
        this.location = location;
        this.description = description;
        this.eventStart_date = eventStart_date;
        this.eventEnd_date = eventEnd_date;
        this.n_participators = n_participators;
        this.type = type;
        this.date = date;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public String getLocation() {
        return location;
    }

    public String getEventStart_date() {
        return eventStart_date;
    }

    public String getDescription() {
        return description;
    }

    public String getEventEnd_date() {
        return eventEnd_date;
    }

    public int getN_participators() {
        return n_participators;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
}
