package org.example.clone.impl;

import org.example.clone.ObjectType;
import org.example.clone.ObjectTypeDeterminer;

import java.util.Map;

public class DefaultObjectTypeDeterminer implements ObjectTypeDeterminer {

    @Override
    public ObjectType determine(Object object) {
        if (object == null) {
            return ObjectType.NULL;
        }
        if (isSimple(object)) {
            return ObjectType.SIMPLE;
        }
        if (object.getClass().isArray()) {
            return ObjectType.ARRAY;
        }
        if (object instanceof Map) {
            return ObjectType.MAP;
        }
        if (object instanceof Iterable) {
            return ObjectType.COLLECTION;
        }

        return ObjectType.COMPLEX;
    }

    private boolean isSimple(Object object) {
        return object.getClass().isPrimitive() || object instanceof String || object instanceof Number;
    }

}
