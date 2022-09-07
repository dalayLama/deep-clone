package org.example.clone.impl.makers;

import org.example.clone.ObjectMetaInfo;

public interface CloneMaker {

    <T> T clone(T object, ObjectMetaInfo metaInfo);

    boolean supports(ObjectMetaInfo metaInfo);

}
