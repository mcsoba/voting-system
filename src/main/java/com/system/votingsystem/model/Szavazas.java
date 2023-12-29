package com.system.votingsystem.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class Szavazas {

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime idopont;

    private String targy;
    private String tipus;
    private String elnok;
    private List<Szavazat> szavazatok;

    public Szavazas(LocalDateTime idopont, String targy, String tipus, String elnok, List<Szavazat> szavazatok) {
        this.idopont = idopont;
        this.targy = targy;
        this.tipus = tipus;
        this.elnok = elnok;
        this.szavazatok = szavazatok;
    }

    public LocalDateTime getIdopont() {
        return idopont;
    }

    public void setIdopont(LocalDateTime idopont) {
        this.idopont = idopont;
    }

    public String getTargy() {
        return targy;
    }

    public void setTargy(String targy) {
        this.targy = targy;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public String getElnok() {
        return elnok;
    }

    public void setElnok(String elnok) {
        this.elnok = elnok;
    }

    public List<Szavazat> getSzavazatok() {
        return szavazatok;
    }

    public void setSzavazatok(List<Szavazat> szavazatok) {
        this.szavazatok = szavazatok;
    }
}

