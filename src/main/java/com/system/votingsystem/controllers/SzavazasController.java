package com.system.votingsystem.controllers;

import com.system.votingsystem.dto.ErrorResponse;
import com.system.votingsystem.dto.SzavazasResponse;
import com.system.votingsystem.model.Szavazas;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/szavazasok")
public class SzavazasController {

    @PostMapping("/szavazas")
    public ResponseEntity<?> rogzitSzavazas(@RequestBody Szavazas szavazas) {
        try {
            // rögzítés + ellenőrzés

            String szavazasId = generateUniqueId();

            return ResponseEntity.ok(new SzavazasResponse(szavazasId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        }
    }

    private String generateUniqueId() {
        return "OJ" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 4).toUpperCase();
    }
}

