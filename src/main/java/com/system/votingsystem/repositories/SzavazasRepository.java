package com.system.votingsystem.repositories;

import com.system.votingsystem.entities.Szavazas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SzavazasRepository extends JpaRepository<Szavazas, String> {
}