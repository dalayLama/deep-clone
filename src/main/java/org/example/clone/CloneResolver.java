package org.example.clone;

public interface CloneResolver {

    <T> T clone(T object, ObjectMetaInfo metaInfo);

}
