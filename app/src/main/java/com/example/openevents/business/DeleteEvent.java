package com.example.openevents.business;

import java.io.Serializable;

public class DeleteEvent implements Serializable {

    private String Mensaje;

    public DeleteEvent(String mensaje) {
        Mensaje = mensaje;
    }

    public String getMensaje() {
        return Mensaje;
    }
}
