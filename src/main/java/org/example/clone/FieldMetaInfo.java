package org.example.clone;

import java.lang.reflect.Field;

public class FieldMetaInfo {

    private final Field field;

    private final Object value;

    private final ObjectMetaInfo fieldMetaInfo;

    public FieldMetaInfo(Field field, Object value, ObjectMetaInfo fieldMetaInfo) {
        this.field = field;
        this.value = value;
        this.fieldMetaInfo = fieldMetaInfo;
    }

    public String getFieldName() {
        return field.getName();
    }

    public Field getField() {
        return field;
    }

    public Object getValue() {
        return value;
    }

    public ObjectMetaInfo getFieldMetaInfo() {
        return fieldMetaInfo;
    }

}
