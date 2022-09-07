package org.example.clone.impl.makers;

import org.example.clone.CloneResolver;
import org.example.clone.ObjectMetaInfo;
import org.example.clone.ObjectType;
import org.example.clone.exceptions.InstanceObjectException;
import org.example.utils.ReflectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.*;

public class ComplexCloneMaker implements CloneMaker {

    private final CloneResolver cloneResolver;

    private final Map<Object, Object> cache = new HashMap<>();

    public ComplexCloneMaker(CloneResolver cloneResolver) {
        this.cloneResolver = cloneResolver;
    }

    @Override
    public <T> T clone(T object, ObjectMetaInfo metaInfo) {
        if (cache.containsKey(object)) {
            return (T) cache.get(object);
        }
        T clone = newInstance(metaInfo);
        cache.put(object, clone);

        Map<String, Object> fieldValues = new HashMap<>(metaInfo.getFields().size());
        metaInfo.getFields()
                .forEach(field -> {
                    Object value = cloneResolver.clone(field.getValue(), field.getFieldMetaInfo());
                    fieldValues.put(field.getFieldName(), value);
                });
        setFields(clone, metaInfo, fieldValues);
        return clone;
    }

    private <T> T newInstance(ObjectMetaInfo metaInfo) {
        Constructor<?>[] constructors = metaInfo.getClazz().getConstructors();
        Constructor<?> constructor = Arrays.stream(constructors)
                .min(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow();
        return (T) instance(constructor);
    }

    private <T> T instance(Constructor<T> constructor) {
        Object[] params = new Object[constructor.getParameterCount()];
        for (int i = 0; i < constructor.getParameterCount(); i++) {
            Parameter parameter = constructor.getParameters()[i];
            params[i] = parameter.getType().isPrimitive() ? 0 : null;
        }
        try {
            return constructor.newInstance(params);
        } catch (Exception e) {
            throw new InstanceObjectException(e);
        }
    }

    private <T> void setFields(T newInstance, ObjectMetaInfo metaInfo, Map<String, Object> fieldValues) {
        metaInfo.getFields().forEach(field -> {
            Object newValue = fieldValues.get(field.getFieldName());
            ReflectionUtils.setField(field.getField(), newInstance, newValue);
        });
    }

    @Override
    public boolean supports(ObjectMetaInfo metaInfo) {
        return metaInfo.getType() == ObjectType.COMPLEX;
    }

}
