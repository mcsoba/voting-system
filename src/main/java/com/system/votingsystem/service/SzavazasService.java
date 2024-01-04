package com.system.votingsystem.service;

import com.system.votingsystem.entities.Szavazas;
import com.system.votingsystem.repositories.SzavazasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SzavazasService {

    private final SzavazasRepository szavazasRepository;

    @Autowired
    public SzavazasService(SzavazasRepository szavazasRepository) {
        this.szavazasRepository = szavazasRepository;
    }

    public Szavazas rogzitSzavazas(Szavazas szavazas) {
        Szavazas szavazasEntity = new Szavazas(szavazas.getIdopont(), szavazas.getTargy(), szavazas.getTipus(), szavazas.getElnok(),
                szavazas.getSzavazatok(), generateUniqueId());
        return szavazasRepository.save(szavazasEntity);
    }

    public Optional<Szavazas> getSzavazasById(String id) {
        return szavazasRepository.findById(id);
    }

    private String generateUniqueId() {
        return "OJ" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4).toUpperCase();
    }
}


