package com.giroux.kevin.dofustuff.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Classe permettant de gérer les exceptions au niveau des WebServices
 * 
 * @author girouxkevin
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -3487645332685643405L;

}
