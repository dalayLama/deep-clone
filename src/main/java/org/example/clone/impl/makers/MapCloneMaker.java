package org.example.clone.impl.makers;

import org.example.clone.CloneResolver;
import org.example.clone.ObjectMetaInfo;
import org.example.clone.ObjectMetaInfoReader;
import org.example.clone.ObjectType;
import org.example.utils.ReflectionUtils;

import java.util.Map;

public class MapCloneMaker implements CloneMaker {

    private final ObjectMetaInfoReader metaInfoReader;

    private final CloneResolver cloneResolver;

    public MapCloneMaker(ObjectMetaInfoReader metaInfoReader, CloneResolver cloneResolver) {
        this.metaInfoReader = metaInfoReader;
        this.cloneResolver = cloneResolver;
    }

    @Override
    public <T> T clone(T object, ObjectMetaInfo metaInfo) {
        Map newMap = (Map) ReflectionUtils.newInstance(object.getClass());
        Map oldMap = (Map) object;
        oldMap.forEach((key, value) -> {
            ObjectMetaInfo elementInfo = metaInfoReader.read(value);
            Object clone = cloneResolver.clone(value, elementInfo);
            newMap.put(key, clone);
        });
        return (T) newMap;
    }

    @Override
    public boolean supports(ObjectMetaInfo metaInfo) {
        return metaInfo.getType() == ObjectType.MAP;
    }

}
