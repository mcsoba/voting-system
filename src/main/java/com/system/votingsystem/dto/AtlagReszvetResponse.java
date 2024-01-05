package com.system.votingsystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.dao.EmptyResultDataAccessException;

public class AtlagReszvetResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "0.00")
    private final double atlag;

    public AtlagReszvetResponse(double atlag) {
        if (atlag == 0.0) {
            throw new EmptyResultDataAccessException("Nincsenek szavazatok a megadott paraméterek között.", 1);
        }
        this.atlag = atlag;
    }

    public double getAtlag() {
        return atlag;
    }
}


