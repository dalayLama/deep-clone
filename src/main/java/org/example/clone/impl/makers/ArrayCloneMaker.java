package org.example.clone.impl.makers;

import org.example.clone.CloneResolver;
import org.example.clone.ObjectMetaInfo;
import org.example.clone.ObjectMetaInfoReader;
import org.example.clone.ObjectType;
import org.example.utils.ReflectionUtils;

import java.lang.reflect.Array;

public class ArrayCloneMaker implements CloneMaker {

    private final ObjectMetaInfoReader metaInfoReader;

    private final CloneResolver cloneResolver;

    public ArrayCloneMaker(ObjectMetaInfoReader metaInfoReader,
                           CloneResolver cloneResolver) {
        this.metaInfoReader = metaInfoReader;
        this.cloneResolver = cloneResolver;
    }

    @Override
    public <T> T clone(T object, ObjectMetaInfo metaInfo) {
        int length = Array.getLength(object);
        Object newArray = ReflectionUtils.newArray(object.getClass().getComponentType(), length);
        for (int i = 0; i < length; i++) {
            Object element = Array.get(object, i);
            ObjectMetaInfo elementInfo = metaInfoReader.read(element);
            Object clone = cloneResolver.clone(element, elementInfo);
            Array.set(newArray, i, clone);
        }
        return (T) newArray;
    }

    @Override
    public boolean supports(ObjectMetaInfo metaInfo) {
        return metaInfo.getType() == ObjectType.ARRAY;
    }
}
