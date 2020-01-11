package com.example.timerapp;

import java.util.Date;

public class Aufgabe {

    private String beschreibung;
    private String aufgabe;
    private Date datum;
    private int id;

    public Aufgabe(int id, String aufgabe, String beschreibung){
        this.id = id;
        this.aufgabe = aufgabe;
        this.beschreibung = beschreibung;

    }

    public String getAufgabe(){
        return aufgabe;
    }

    public void setAufgabe(String aufgabe){
        this.aufgabe = aufgabe;
    }

    public String getBeschreibung(){
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung){
        this.beschreibung = beschreibung;
    }

    public int getId() {
        return id;
    }
}
