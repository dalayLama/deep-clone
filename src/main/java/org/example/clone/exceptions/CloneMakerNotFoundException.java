package org.example.clone.exceptions;

import org.example.clone.ObjectMetaInfo;

public class CloneMakerNotFoundException extends RuntimeException {
    private final ObjectMetaInfo metaInfo;

    public CloneMakerNotFoundException(ObjectMetaInfo metaInfo) {
        this.metaInfo = metaInfo;
    }
}
