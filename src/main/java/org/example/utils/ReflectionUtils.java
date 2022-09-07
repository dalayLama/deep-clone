package org.example.utils;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public final class ReflectionUtils {

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Object newArray(Class<?> clazz, int length) {
        try {
            return Array.newInstance(clazz, length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setField(Field field, Object target, Object newValue) {
        try {
            field.setAccessible(true);
            field.set(target, newValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
