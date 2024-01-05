package com.system.votingsystem.dto;

import com.system.votingsystem.types.EredmenyTipus;

public class SzavazasEredmenyResponse {

    private EredmenyTipus eredmeny;
    private int kepviselokSzama;
    private int igenekSzama;
    private int nemekSzama;
    private int tartozkodasokSzama;

    public SzavazasEredmenyResponse() {
    }

    public SzavazasEredmenyResponse(EredmenyTipus eredmeny, int kepviselokSzama, int igenekSzama, int nemekSzama, int tartozkodasokSzama) {
        this.eredmeny = eredmeny;
        this.kepviselokSzama = kepviselokSzama;
        this.igenekSzama = igenekSzama;
        this.nemekSzama = nemekSzama;
        this.tartozkodasokSzama = tartozkodasokSzama;
    }

    public EredmenyTipus getEredmeny() {
        return eredmeny;
    }

    public void setEredmeny(EredmenyTipus eredmeny) {
        this.eredmeny = eredmeny;
    }

    public int getKepviselokSzama() {
        return kepviselokSzama;
    }

    public void setKepviselokSzama(int kepviselokSzama) {
        this.kepviselokSzama = kepviselokSzama;
    }

    public int getIgenekSzama() {
        return igenekSzama;
    }

    public void setIgenekSzama(int igenekSzama) {
        this.igenekSzama = igenekSzama;
    }

    public int getNemekSzama() {
        return nemekSzama;
    }

    public void setNemekSzama(int nemekSzama) {
        this.nemekSzama = nemekSzama;
    }

    public int getTartozkodasokSzama() {
        return tartozkodasokSzama;
    }

    public void setTartozkodasokSzama(int tartozkodasokSzama) {
        this.tartozkodasokSzama = tartozkodasokSzama;
    }
}
