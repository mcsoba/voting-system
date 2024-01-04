package com.system.votingsystem.exceptions;

public class DuplikaltSzavazasException extends RuntimeException {
    public DuplikaltSzavazasException(String message) {
        super(message);
    }
}

