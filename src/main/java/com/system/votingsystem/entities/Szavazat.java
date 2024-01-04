package com.system.votingsystem.entities;

import com.system.votingsystem.types.SzavazasTipus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

@Entity
public class Szavazat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Képviselő neve nem lehet üres")
    private String kepviselo;

    @Pattern(regexp = "[i|n|t]", message = "A szavazat csak 'i', 'n' vagy 't' lehet")
    @Column(name = "szavazat", length = 1)
    private String szavazat;

    @ManyToOne
    @JoinColumn(name = "szavazas_id")
    private Szavazas szavazas;

    public Szavazat() {
    }

    public Szavazat(String kepviselo, SzavazasTipus szavazat) {
        this.kepviselo = kepviselo;
        this.szavazat = szavazat.name();
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

