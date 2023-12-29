package com.system.votingsystem.model;

public class Szavazat {

    private String kepviselo;
    private String szavazat;

    public Szavazat(String kepviselo, String szavazat) {
        this.kepviselo = kepviselo;
        this.szavazat = szavazat;
    }

    public String getKepviselo() {
        return kepviselo;
    }

    public void setKepviselo(String kepviselo) {
        this.kepviselo = kepviselo;
    }

    public String getSzavazat() {
        return szavazat;
    }

    public void setSzavazat(String szavazat) {
        this.szavazat = szavazat;
    }
}
