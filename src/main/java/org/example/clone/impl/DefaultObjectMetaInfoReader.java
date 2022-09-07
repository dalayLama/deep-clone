package org.example.clone.impl;

import org.example.clone.*;
import org.example.clone.exceptions.ReadFieldException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class DefaultObjectMetaInfoReader implements ObjectMetaInfoReader {

    private final ObjectTypeDeterminer determiner;

    public DefaultObjectMetaInfoReader(ObjectTypeDeterminer determiner) {
        this.determiner = determiner;
    }

    @Override
    public ObjectMetaInfo read(Object object) {
        ObjectType type = determiner.determine(object);
        if (type == ObjectType.COMPLEX) {
            Collection<? extends FieldMetaInfo> fields = readFields(object);
            return new ObjectMetaInfo(type, object.getClass(), fields);
        }
        return type == ObjectType.NULL ?
                new ObjectMetaInfo(type) : new ObjectMetaInfo(type, object.getClass());
    }

    private Collection<? extends FieldMetaInfo> readFields(Object object) {
        Field[] declaredFields = object.getClass().getDeclaredFields();
        return Arrays.stream(declaredFields)
                .map(field -> readField(object, field))
                .collect(Collectors.toList());
    }

    private FieldMetaInfo readField(Object object, Field field) {
        try {
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            ObjectMetaInfo metaInfo = read(fieldValue);
            return new FieldMetaInfo(field, fieldValue, metaInfo);
        } catch (Exception e) {
            throw new ReadFieldException(field, e);
        }
    }

}
