package com.platformer.exceptions;

import com.platformer.model.Element;

/**
 * Created by Oleh on 04.12.2014.
 */
public class WrongElementTypeException extends Exception {

    public WrongElementTypeException(Element elm) {
        super("Unexpected Element type: " + elm.getClass());
    }
}
