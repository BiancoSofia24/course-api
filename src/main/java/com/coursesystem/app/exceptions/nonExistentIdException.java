package com.coursesystem.app.exceptions;

public class nonExistentIdException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public nonExistentIdException (String text) {
        super(text);
    }
}
