package com.Imanol.SpringBootProyecto.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LibroBadRequestException extends LibroException{
    public LibroBadRequestException(String mensaje) {
        super(mensaje);
    }
}
