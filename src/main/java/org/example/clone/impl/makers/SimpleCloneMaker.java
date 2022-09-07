package org.example.clone.impl.makers;

import org.example.clone.ObjectMetaInfo;
import org.example.clone.ObjectType;

public class SimpleCloneMaker implements CloneMaker {
    @Override
    public <T> T clone(T object, ObjectMetaInfo metaInfo) {
        return object;
    }

    @Override
    public boolean supports(ObjectMetaInfo metaInfo) {
        return metaInfo.getType() == ObjectType.SIMPLE;
    }

}
