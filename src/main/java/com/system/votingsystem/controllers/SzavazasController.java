package com.system.votingsystem.controllers;


import com.system.votingsystem.dto.ErrorResponse;
import com.system.votingsystem.dto.SzavazasResponse;
import com.system.votingsystem.dto.SzavazatResponse;
import com.system.votingsystem.entities.Szavazas;
import com.system.votingsystem.exceptions.DuplikaltIdoException;
import com.system.votingsystem.exceptions.DuplikaltSzavazasException;
import com.system.votingsystem.service.SzavazasService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.*;


@RestController
@RequestMapping("/szavazasok")
public class SzavazasController {

    private final SzavazasService szavazasService;

    public SzavazasController(SzavazasService szavazasService) {
        this.szavazasService = szavazasService;
    }

    @PostMapping("/szavazas")
    public ResponseEntity<?> szavazasRegisztracio(@Valid @RequestBody Szavazas szavazas) {
        try {
            boolean elnokSzavazott = szavazasService.elnokSzavazott(szavazas);
            if (!elnokSzavazott) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Az elnök nem szavazott."));
            }
            szavazasService.validateKepviselokSzavazas(szavazas);
            szavazasService.validateIdopont(szavazas.getIdopont());
            Szavazas szavazasToValidate = szavazasService.rogzitSzavazas(szavazas);
            return ResponseEntity.ok(new SzavazasResponse(szavazasToValidate.getSzavazasId()));
        } catch (DuplikaltSzavazasException | DuplikaltIdoException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error) .getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
                });
        return errors;
    }

    //lekérés
    @GetMapping("/szavazat/{id}")
    public ResponseEntity<?> getSzavazasById(@PathVariable String id) {
        Optional<Szavazas> szavazasEntity = szavazasService.getSzavazasById(id);
        return szavazasEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/szavazat/")
    public ResponseEntity<SzavazatResponse> getKepviseloSzavazat(
            @RequestParam(value = "szavazas") String szavazas,
            @RequestParam(value = "kepviselo") String kepviselo) {
        try {
            Optional<String> szavazat = szavazasService.getKepviseloSzavazat(szavazas, kepviselo);
            return szavazat.map(s -> ResponseEntity.ok(new SzavazatResponse(s)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new SzavazatResponse(e.getMessage()));
        }
    }





}

