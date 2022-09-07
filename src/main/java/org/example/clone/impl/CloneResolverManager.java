package org.example.clone.impl;

import org.example.clone.CloneResolver;
import org.example.clone.ObjectMetaInfo;
import org.example.clone.ObjectType;
import org.example.clone.impl.makers.CloneMaker;
import org.example.clone.exceptions.CloneMakerNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CloneResolverManager implements CloneResolver {

    private final List<CloneMaker> cloneMakers;

    public CloneResolverManager(Collection<? extends CloneMaker> cloneMakers) {
        this.cloneMakers = new ArrayList<>(cloneMakers);
    }

    public CloneResolverManager() {
        this.cloneMakers = new ArrayList<>(ObjectType.values().length);
    }

    public void addResolvers(Collection<? extends CloneMaker> cloneMakers) {
        this.cloneMakers.addAll(cloneMakers);
    }

    @Override
    public <T> T clone(T object, ObjectMetaInfo metaInfo) {
        CloneMaker cloneMaker = cloneMakers.stream()
                .filter(cm -> cm.supports(metaInfo))
                .findFirst()
                .orElseThrow(() -> new CloneMakerNotFoundException(metaInfo));
        return cloneMaker.clone(object, metaInfo);
    }

}
