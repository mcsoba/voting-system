package com.system.votingsystem.repositories;

import com.system.votingsystem.entities.Szavazas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface SzavazasRepository extends JpaRepository<Szavazas, String> {
    boolean existsByidopont(LocalDateTime idopont);
}