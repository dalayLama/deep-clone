package org.example.clone.impl.makers;

import org.example.clone.CloneResolver;
import org.example.clone.ObjectMetaInfo;
import org.example.clone.ObjectMetaInfoReader;
import org.example.clone.ObjectType;
import org.example.utils.ReflectionUtils;

import java.util.Collection;

public class CollectionCloneMaker implements CloneMaker {

    private final ObjectMetaInfoReader metaInfoReader;

    private final CloneResolver cloneResolver;

    public CollectionCloneMaker(ObjectMetaInfoReader metaInfoReader,
                                CloneResolver cloneResolver) {
        this.metaInfoReader = metaInfoReader;
        this.cloneResolver = cloneResolver;
    }

    @Override
    public <T> T clone(T object, ObjectMetaInfo metaInfo) {
        Collection newCollection = (Collection) ReflectionUtils.newInstance(object.getClass());
        Collection oldCollection = (Collection) object;
        for (Object element : oldCollection) {
            ObjectMetaInfo elementInfo = metaInfoReader.read(element);
            Object clone = cloneResolver.clone(element, elementInfo);
            newCollection.add(clone);
        }
        return (T) newCollection;
    }

    @Override
    public boolean supports(ObjectMetaInfo metaInfo) {
        return metaInfo.getType() == ObjectType.COLLECTION;
    }

}
