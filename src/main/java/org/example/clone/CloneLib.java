package org.example.clone;

public class CloneLib {

    private final ObjectMetaInfoReader metaInfoReader;

    private final CloneResolver cloneResolver;

    public CloneLib(ObjectMetaInfoReader metaInfoReader, CloneResolver cloneResolver) {
        this.metaInfoReader = metaInfoReader;
        this.cloneResolver = cloneResolver;
    }

    public <T> T deepCopy(T object) {
        ObjectMetaInfo metaInfo = metaInfoReader.read(object);
        return cloneResolver.clone(object, metaInfo);
    }

}
