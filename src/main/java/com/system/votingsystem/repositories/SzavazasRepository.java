package com.system.votingsystem.repositories;

import com.system.votingsystem.entities.Szavazas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface SzavazasRepository extends JpaRepository<Szavazas, String> {
    boolean existsByidopont(LocalDateTime idopont);


    default int getUtolsoSzavazasJelenlevok(String szavazasId) {
        Optional<Szavazas> szavazasOptional = findById(szavazasId);
        if (szavazasOptional.isPresent()) {
            Szavazas szavazas = szavazasOptional.get();
            return szavazas.getSzavazatok().size();
        }
        return 200;
    }
    @Query("SELECT COUNT(s) FROM Szavazat s JOIN s.szavazas sz WHERE sz.tipus <> 'j' AND sz.idopont BETWEEN :startDate AND :endDate")
    long totalSzavazatokCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(DISTINCT sz.id) FROM Szavazas sz WHERE sz.idopont BETWEEN :startDate AND :endDate AND sz.tipus <> 'j'")
    long distinctSzavazatCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}




