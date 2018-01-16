package com.giroux.kevin.dofustuff.security.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe permettant de gérer les exceptions au niveau des WebServices
 *
 * @author girouxkevin
 *
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = -3487645332685643405L;

    /**
     * Pass a message for the exception
     *
     */
    public UnauthorizedException() {
        super();
    }
}