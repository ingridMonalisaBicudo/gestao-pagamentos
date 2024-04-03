package com.project.gestaopagamentos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IOException extends Exception{
    public IOException(String message) {
        super(message);
    }
}
