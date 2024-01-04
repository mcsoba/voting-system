package com.system.votingsystem.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum SzavazasTipus {
    @JsonProperty("j")
    JELENLET,

    @JsonProperty("e")
    EGYSZERU_TOBBSEG,

    @JsonProperty("m")
    MINOSEGITETT_TOBBSEG
    }


