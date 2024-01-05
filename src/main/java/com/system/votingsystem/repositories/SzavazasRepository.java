package com.system.votingsystem.repositories;

import com.system.votingsystem.entities.Szavazas;
import com.system.votingsystem.entities.Szavazat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Repository
public interface SzavazasRepository extends JpaRepository<Szavazas, String> {
    boolean existsByidopont(LocalDateTime idopont);

    @Query("SELECT s FROM Szavazas s WHERE s.tipus = 'JELENLET' ORDER BY s.idopont DESC")
    Optional<Szavazas> keresUtolsoJelenletSzavazas();

    @Query("SELECT s FROM Szavazat s WHERE s.szavazas.szavazasId = :szavazasId")
    List<Szavazat> keresSzavazatok(@Param("szavazasId") String szavazasId);

    @Query("SELECT COUNT(s) FROM Szavazat s WHERE s.szavazas.szavazasId = :szavazasId AND s.szavazat = 'i'")
    int countIgenSzavazatokBySzavazas(@Param("szavazasId") String szavazasId);

    @Query("SELECT COUNT(s) FROM Szavazat s WHERE s.szavazas.szavazasId = :szavazasId AND s.szavazat = 'n'")
    int countNemSzavazatokBySzavazas(@Param("szavazasId") String szavazasId);

    @Query("SELECT COUNT(s) FROM Szavazat s WHERE s.szavazas.szavazasId = :szavazasId AND s.szavazat = 't'")
    int countTartozkodasSzavazatok(@Param("szavazasId") String szavazasId);

    @Query("SELECT COUNT(s) FROM Szavazat s JOIN s.szavazas sz WHERE sz.tipus <> 'j' AND sz.idopont BETWEEN :startDate AND :endDate")
    long totalSzavazatokCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(DISTINCT sz.id) FROM Szavazas sz WHERE sz.idopont BETWEEN :startDate AND :endDate AND sz.tipus <> 'j'")
    long distinctSzavazatCount(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}




