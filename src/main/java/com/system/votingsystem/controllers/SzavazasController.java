package com.system.votingsystem.controllers;


import com.system.votingsystem.dto.*;
import com.system.votingsystem.entities.Szavazas;
import com.system.votingsystem.exceptions.DuplikaltIdoException;
import com.system.votingsystem.exceptions.DuplikaltSzavazasException;
import com.system.votingsystem.service.SzavazasService;
import com.system.votingsystem.types.EredmenyTipus;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;


@RestController
@RequestMapping("/szavazasok")
public class SzavazasController {

    private static final Logger logger = LoggerFactory.getLogger(SzavazasController.class);

    private final SzavazasService szavazasService;

    public SzavazasController(SzavazasService szavazasService) {
        this.szavazasService = szavazasService;
    }

    @PostMapping("/szavazas")
    public ResponseEntity<?> szavazasRegisztracio(@Valid @RequestBody Szavazas szavazas) {
        try {
            logger.info("Request received for /szavazas");
            boolean elnokSzavazott = szavazasService.elnokSzavazott(szavazas);
            if (!elnokSzavazott) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Az elnök nem szavazott."));
            }
            szavazasService.validateKepviselokSzavazas(szavazas);
            szavazasService.validateIdopont(szavazas.getIdopont());
            Szavazas szavazasToValidate = szavazasService.rogzitSzavazas(szavazas);
            return ResponseEntity.ok(new SzavazasResponse(szavazasToValidate.getSzavazasId()));
        } catch (DuplikaltSzavazasException | DuplikaltIdoException e) {
            logger.error("Request received for /szavazas");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            logger.error("Request received for /szavazas");
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @GetMapping("/szavazat/")
    public ResponseEntity<SzavazatResponse> getKepviseloSzavazat(
            @RequestParam(value = "szavazas") String szavazas,
            @RequestParam(value = "kepviselo") String kepviselo) {
        try {
            logger.info("Request received for /szavazat");
            Optional<String> szavazat = szavazasService.getKepviseloSzavazat(szavazas, kepviselo);
            return szavazat.map(s -> ResponseEntity.ok(new SzavazatResponse(s)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Request received for /szavazat");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SzavazatResponse(e.getMessage()));
        }
    }

    @GetMapping("/kepviselo-reszvetel-atlag/")
    public ResponseEntity<?> getAtlagReszvet(
            @RequestParam("start-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("end-date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        try {
            logger.info("Request received for /kepviselo-reszvetel-atlag");
            double atlagReszvetel = szavazasService.calcAtlagReszvet(startDate, endDate);
            AtlagReszvetResponse response = new AtlagReszvetResponse(atlagReszvetel);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Request received for /kepviselo-reszvetel-atlag");
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/eredmeny/{szavazasId}")
    public ResponseEntity<?> getEredmenyBySzavazasId(@PathVariable String szavazasId) {
        try {
            logger.info("Request received for /eredmeny");
            EredmenyTipus eredmeny = szavazasService.calculateSzavazasEredmeny(szavazasId);
            return ResponseEntity.ok(new SzavazasEredmenyResponse(eredmeny, szavazasService.getJelenlevokSzama(), szavazasService.getIgenekSzama(szavazasId),
                    szavazasService.getNemekSzama(szavazasId), szavazasService.getTartozkodasokSzama(szavazasId)));
        } catch (Exception e) {
            logger.error("Request received for /eredmeny");
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/napi-szavazasok/{date}")
    public ResponseEntity<?> getNapiSzavazasok(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            logger.info("Request received for /napi-szavazasok");
            List<Szavazas> szavazasok = szavazasService.keresSzavazasByDate(date);
            Map<String, List<Szavazas>> response = new HashMap<>();
            response.put("szavazasok", szavazasok);
            return ResponseEntity.ok(response);
    } catch (Exception e) {
        logger.error("Request received for /napi-szavazasok");
        return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
    }

    }
}
