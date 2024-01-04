package com.system.votingsystem.entities;

import com.system.votingsystem.types.SzavazasTipus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Szavazas {

    @Id
    @Column(name = "szavazas_id", unique = true)
    private String szavazasId;
    @NotNull(message = "A dátum nem lehet nulla.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime idopont;
    @NotNull(message = "A tárgy nem lehet nulla.")
    @NotEmpty(message = "A tárgy nem lehet üres.")
    private String targy;

    @Enumerated(EnumType.STRING)
    private SzavazasTipus tipus;
    @NotNull(message = "Az elnök nem lehet nulla.")
    @NotEmpty(message = "Az elnök nem lehet üres.")
    private String elnok;

    //    @NotNull(message = "A szavazat nem lehet nulla")
    @OneToMany(mappedBy = "szavazas", cascade = CascadeType.ALL)
    private List<Szavazat> szavazatok;

    public Szavazas(LocalDateTime idopont, String targy, SzavazasTipus tipus, String elnok, List<Szavazat> szavazatok, String szavazasId) {
        this.idopont = idopont;
        this.targy = targy;
        this.tipus = tipus;
        this.elnok = elnok;
        this.szavazatok = szavazatok;
        this.szavazasId = szavazasId;
    }

    public Szavazas()
    {

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

    public SzavazasTipus getTipus() {
        return tipus;
    }

    public void setTipus(SzavazasTipus tipus) {
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
    public String getSzavazasId() {
        return szavazasId;
    }
}

