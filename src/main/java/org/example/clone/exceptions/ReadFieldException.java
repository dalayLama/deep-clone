package org.example.clone.exceptions;

import java.lang.reflect.Field;

public class ReadFieldException extends RuntimeException {

    private final Field field;

    public ReadFieldException(Field field, Throwable e) {
        super(e);
        this.field = field;
    }

    public Field getField() {
        return field;
    }
}
