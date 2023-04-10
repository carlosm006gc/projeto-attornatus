package com.dev.testeAttornatus.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ObjectNotFoundException extends RuntimeException {

    public ObjectNotFoundException(Class clazz, Long id) {
        super(String.format("%s com o ID %d não foi encontrado.", clazz.getSimpleName(), id));
    }
}

