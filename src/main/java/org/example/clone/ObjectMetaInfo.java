package org.example.clone;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ObjectMetaInfo {

    private final ObjectType type;

    private final Class<?> clazz;

    private final List<FieldMetaInfo> fields;

    public ObjectMetaInfo(ObjectType type, Class<?> clazz, Collection<? extends FieldMetaInfo> fields) {
        this.type = type;
        this.clazz = clazz;
        this.fields = new ArrayList<>(fields);
    }

    public ObjectMetaInfo(ObjectType type, Class<?> clazz) {
        this(type, clazz, Collections.emptyList());
    }

    public ObjectMetaInfo(ObjectType type) {
        this(type, null, Collections.emptyList());
    }

    public ObjectType getType() {
        return type;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public List<FieldMetaInfo> getFields() {
        return Collections.unmodifiableList(fields);
    }

}
