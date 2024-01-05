package com.system.votingsystem.types;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum EredmenyTipus {
    @JsonProperty("F")
    ELFOGADOTT,

    @JsonProperty("U")
    ELUTASITOTT

}
