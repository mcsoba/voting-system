package com.system.votingsystem.service;

import com.system.votingsystem.entities.Szavazas;
import com.system.votingsystem.entities.Szavazat;
import com.system.votingsystem.exceptions.DuplikaltIdoException;
import com.system.votingsystem.exceptions.DuplikaltSzavazasException;
import com.system.votingsystem.repositories.SzavazasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
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

    public boolean elnokSzavazott(Szavazas szavazas) {
        if (szavazas.getSzavazatok() == null) {
            return false;
        }
        for (Szavazat szavazat : szavazas.getSzavazatok()) {
            if (szavazas.getElnok().equals(szavazat.getKepviselo())) {
                return true;
            }
        }
        return false;
    }

    public void validateKepviselokSzavazas(Szavazas szavazas) {
        Set<String> szavazottKepviselok = new HashSet<>();

        for (Szavazat szavazat : szavazas.getSzavazatok()) {
            String kepviselo = szavazat.getKepviselo();

            if (szavazottKepviselok.contains(kepviselo)) {
                throw new DuplikaltSzavazasException("A képviselőnek már van szavazata.");
            }
            szavazottKepviselok.add(kepviselo);
        }
    }

    public void validateIdopont(LocalDateTime idopont) {
        if (szavazasRepository.existsByidopont(idopont)) {
            throw new DuplikaltIdoException("Már történt szavazás ezen az időponton.");
        }
    }

    public Optional<String> getKepviseloSzavazat(String szavazasId, String kepviseloId) {
        Optional<Szavazas> szavazasOptional = szavazasRepository.findById(szavazasId);
        if (szavazasOptional.isPresent()) {
            Szavazas szavazas = szavazasOptional.get();
            for (Szavazat szavazat : szavazas.getSzavazatok()) {
                if (szavazat.getKepviselo().equals(kepviseloId)) {
                    return Optional.of(szavazat.getSzavazat());
                }
            }
        }
        return Optional.empty();
    }

    public int szavazasJelenlevokSzama(String szavazasId){
        return szavazasRepository.getUtolsoSzavazasJelenlevok(szavazasId);
    }

    public double calcAtlagReszvet(LocalDateTime startDate, LocalDateTime endDate) {
        long totalSzavazatok = szavazasRepository.totalSzavazatokCount(startDate, endDate);
        long totalKepviselok = szavazasRepository.distinctSzavazatCount(startDate, endDate);
        double atlagReszvetel = totalKepviselok > 0 ? (double) totalSzavazatok / totalKepviselok : 0.0;
        return Math.round(atlagReszvetel * 100.0) / 100.0;
    }


}


